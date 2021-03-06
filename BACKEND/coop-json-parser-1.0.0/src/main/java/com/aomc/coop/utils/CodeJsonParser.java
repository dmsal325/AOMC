package com.aomc.coop.utils;

import com.aomc.coop.model.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class CodeJsonParser<T> {

    private static CodeJsonParser instance = getInstance();

    private CodeJsonParser() {

    }

    public static CodeJsonParser getInstance() {
        if(instance == null){
            synchronized (CodeJsonParser.class) {
                if(instance == null)
                    instance = new CodeJsonParser();
            }

        }
        return instance;

    }

    Response response = new Response();

    public ResponseType codeJsonParser(String errorNum){

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("../../COMMON/code.json"));


            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resp = (JSONObject) jsonObject.get(errorNum);

            if(resp == null) {
                return  ResponseType.res(500, "서버 오류", "Intenal Server Error");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String r = objectMapper.writeValueAsString(resp);
            response = objectMapper.readValue(r, Response.class);


            return  ResponseType.res(response.getStatus(), response.getMessage(), response.getDescription());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  ResponseType.res(404, "파일 없음", "File Not Found");

    }

    public ResponseType<T> codeJsonParser(String errorNum, final T data){

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("../../COMMON/code.json"));


            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resp = (JSONObject) jsonObject.get(errorNum);

            if(resp == null) {
                return  ResponseType.res(500, "서버 오류", "Intenal Server Error");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String r = objectMapper.writeValueAsString(resp);
            response = objectMapper.readValue(r, Response.class);


            return  ResponseType.res(response.getStatus(), response.getMessage(), response.getDescription(), data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  ResponseType.res(404, "파일 없음", "File Not Found");

    }

    public ResponseType<T> codeJsonParser(String errorNum, final T data, final T plusData){

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("../../COMMON/code.json"));


            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resp = (JSONObject) jsonObject.get(errorNum);

            if(resp == null) {
                return  ResponseType.res(500, "서버 오류", "Intenal Server Error");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String r = objectMapper.writeValueAsString(resp);
            response = objectMapper.readValue(r, Response.class);


            return  ResponseType.res(response.getStatus(), response.getMessage(), response.getDescription(), data, plusData);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  ResponseType.res(404, "파일 없음", "File Not Found");

    }

}