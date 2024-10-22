package org.example.apitest.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.example.resthandlers.apihandlers.OrdersHandlerAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-CreateOrder")
@Tag("create-order")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API создания заказа")
@DisplayName("3. Создание заказа")
public class CreateOrderTests extends OrdersHandlerAPI {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.firstName = "Петя";
        this.lastName = "Иванов";
        this.address = "Москва, Пушкина ул., д. 12";
        this.phone = "+7 (910) 274-96-73";
        this.rentTime = "2";
        this.deliveryDate = "2024-10-21";
        this.comment = "Не звонить";
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        if (trackId == null) return;

        deleteOrder(trackId);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест проверяет API создания заказа. Ожидаемый результат - заказ создан, возвращается его track-номер")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", scooterColor);


        Response response = createOrder(firstName, lastName, address, phone, rentTime, deliveryDate, comment, scooterColor)
                .then() // добавляем эту строку для логирования
                .log().all() // логируем весь запрос и ответ
                .extract().response(); // извлекаем ответ
        checkStatusCode(response, 201);
        checkResponseParamNotNull(response, "track");

        this.trackId = getTrack(response);
    }

}
