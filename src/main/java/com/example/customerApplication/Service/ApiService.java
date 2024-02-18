package com.example.customerApplication.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@Service
public class ApiService {

    public static final String loginUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    public static final String customerApi = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    public static Object[] getTokenFromApi() {

        String requestBody = "{\"login_id\":\"test@sunbasedata.com\", \"password\":\"Test@123\"}";
        //calling api to get token
        String token = callApi(loginUrl, requestBody);
        //token retriving
        String newToken = token.substring(19, token.length()-3);
        //calling customerApi to get customer
        List<Object> customerList = getCustomerList(customerApi,newToken);

        //returning data
        Object[] customerReceived =customerList.toArray();
        return customerReceived;

    }

    private static List<Object> getCustomerList(String customerApi, String newToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + newToken);

        HttpEntity<String> requestEntity =new HttpEntity<>(headers);

        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(
                customerApi,
                HttpMethod.GET,
                requestEntity,
                Object[].class
        );

        Object[] responseBody = responseEntity.getBody();
        return List.of(responseBody);

    }

    private static String callApi(String loginUrl, String requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        //setting content type for header
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestedEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(loginUrl,
                                                                HttpMethod.POST,
                                                                requestedEntity,
                                                                String.class
                );
        String responseBody =responseEntity.getBody();
        return  responseBody;
    }
}
