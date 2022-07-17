package com.kaydash_dmitriy.telegrambot.listener;

import com.kaydash_dmitriy.telegrambot.entity.*;
import com.kaydash_dmitriy.telegrambot.sevices.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.kaydash_dmitriy.telegrambot.listener.TelegramButtons.*;
import static com.kaydash_dmitriy.telegrambot.listener.TelegramMessages.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TelegramBotConnection implements UpdatesListener {
    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderProductService orderProductService;

    @Resource
    @Qualifier("telegramBot")
    private TelegramBot telegramBot;

    @PostConstruct
    public void createConnection() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(this::processUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(Update update) {
        if (update.callbackQuery() != null) {
            addToCart(update);
        } else {
            sendStartMessage(update);
            formAndSendReply(update);
            formAndSendCategory(update);
        }
    }

    private void sendStartMessage(Update update) {
        Client client = clientService.getClientByExternalId(getExternalId(update));

        if (client == null) {
            registerClient(update);
            return;
        }

        if (update.message().text() != null && update.message().text().equals("/start")) {
            mainMenu(update);
        }
    }

    private void formAndSendReply(Update update) {
        switch (update.message().text()) {
            case BACK_TO_MENU_BUTTON: {
                mainMenu(update);
                break;
            }
            case MENU_WATCH_CATEGORIES_BUTTON:
            case BACK_TO_MENU_WATCH_CATEGORIES_BUTTON: {
                watchCategories(update);
                break;
            }
            case CREATE_ORDER_BUTTON: {
                closeCurrentOrder(update);
                break;
            }
            case MENU_MAKE_ORDERS_BUTTON: {
                showCurrentOrder(update);
                break;
            }
        }
    }

    private void registerClient(Update update) {
        String[] userInfo = update.message().text().split("\n");

        if (userInfo.length < 3) {
            sendMessage(update, REGISTRATION_INPUT_MESSAGE);
            return;
        }
        Client client = new Client(userInfo[0], userInfo[1], userInfo[2], getExternalId(update));
        clientService.createClient(client);
    }

    private void formAndSendCategory(Update update) {
        if (update.message().text() != null) {
            Category category = categoryService.getCategoryByName(update.message().text());

            if (category != null) {
                List<Product> products = productService.getProductsByCategoryId(category.getId());
                if (products.size() > 0) {
                    watchCategoryProducts(update, category, products);
                } else {
                    watchCategories(update, category.getId());
                }
            }
        }
    }

    private void mainMenu(Update update) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                MENU_WATCH_CATEGORIES_BUTTON,
                MENU_MAKE_ORDERS_BUTTON
        ).oneTimeKeyboard(true).resizeKeyboard(true)
                .selective(true);

        sendMessageWithKeyboard(update, START_MESSAGE, keyboardMarkup);
    }

    private void watchCategories(Update update) {
        List<Category> categories = categoryService.getCategoryByParentId(CategoryService.PARENT_CATEGORY_ID);

        String[] categoriesButtonsText = categories.stream().map(Category::getName).toArray(String[]::new);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                categoriesButtonsText,
                new String[]{BACK_TO_MENU_BUTTON},
                new String[]{ CREATE_ORDER_BUTTON }
        ).oneTimeKeyboard(true).resizeKeyboard(true)
                .selective(true);

        sendMessageWithKeyboard(update, CATEGORY_SELECT_MESSAGE, keyboardMarkup);
    }

    private void watchCategories(Update update, Long categoryId) {
        List<Category> categories = categoryService.getCategoryByParentId(categoryId);

        String[] categoriesButtonsText = categories.stream().map(Category::getName).toArray(String[]::new);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                categoriesButtonsText,
                new String[]{ BACK_TO_MENU_BUTTON },
                new String[]{ CREATE_ORDER_BUTTON }
        ).oneTimeKeyboard(true).resizeKeyboard(true)
                .selective(true);

        sendMessageWithKeyboard(update, CATEGORY_SELECT_MESSAGE, keyboardMarkup);
    }

    private void watchCategoryProducts(Update update, Category category, List<Product> products) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        products.forEach(product -> {
            markup.addRow(new InlineKeyboardButton(
                    String.format("Товар: %s. Цена %.2f руб.", product.getName(), product.getPrice())
            ).callbackData(
                    String.format("%s", product.getName())
            ));
        });

        sendMessageWithKeyboard(update, category.getName(), markup);
    }

    private void addToCart(Update update) {
        String productName = update.callbackQuery().data();
        Long clientExternalId = getExternalId(update);

        orderProductService.addToCart(clientExternalId, productName);

        sendMessage(update, ORDER_CREATED_SUCC_MESSAGE);
    }

    private void closeCurrentOrder(Update update) {
        String currentOrder = getCurrentOrderInTextForm(update);
        if (!currentOrder.equals(CART_EMPTY_MESSAGE)) {
            clientOrderService.closeClientOrderByExternalId(getExternalId(update));

            sendMessage(update, ORDER_FORMED_SUCC_MESSAGE);
        }
        sendMessage(update, currentOrder);
    }

    private void showCurrentOrder(Update update) {
        sendMessage(update, getCurrentOrderInTextForm(update));
    }

    private String getCurrentOrderInTextForm(Update update) {
        ClientOrder clientOrder = clientOrderService.getOpenOrderByClientExternalId(getExternalId(update));

        if (clientOrder != null) {
            StringBuilder clientOrderInTextForm = new StringBuilder();

            List<OrderProduct> orderProducts = orderProductService.getOrderByClientOrder(clientOrder);
            orderProducts.forEach(orderProduct -> {
                clientOrderInTextForm.append(String.format("Товар: %s; Количество: %d; Итого: %f\n",
                        orderProduct.getProduct().getName(),
                        orderProduct.getCountProduct(),
                        orderProduct.getCountProduct() * orderProduct.getProduct().getPrice()));
            });

            clientOrderInTextForm.append(String.format("\n\nИтого: %f", orderProducts.get(0).getClientOrder().getTotal()));

            return clientOrderInTextForm.toString();
        }
        else {
            return CART_EMPTY_MESSAGE;
        }
    }

    private void sendMessageWithKeyboard(Update update, String message, Keyboard keyboardMarkup) {
        Long chatId = getExternalId(update);
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message).replyMarkup(keyboardMarkup));
    }

    private void sendMessage(Update update, String message) {
        Long chatId = getExternalId(update);
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
    }

    private Long getExternalId(Update update) {
        if (update.callbackQuery() != null) {
            return update.callbackQuery().message().chat().id();
        }
        return update.message().chat().id();
    }
}
