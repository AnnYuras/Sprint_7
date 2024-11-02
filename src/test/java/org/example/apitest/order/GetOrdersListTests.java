package org.example.apitest.order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.example.resthandlers.apihandlers.OrdersAPIHandler;
import org.junit.Test;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-GetOrdersPageByPage")
@Tag("get-orders-list")
@Epic("Sprint 7. Project")
@Feature("Группа тестов для API получения списка заказа")
@DisplayName("4. Получение списка заказов")
public class GetOrdersListTests extends OrdersAPIHandler {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Тест проверяет API получения списка заказов. Ожидаемый результат - возвращается список заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        Response response = getOrdersList()
                .then() // добавляем эту строку для логирования
                .log().all() // логируем весь запрос и ответ
                .extract().response(); // извлекаем ответ

        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
    }
}
