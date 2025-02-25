package com.assertsl.workshop.beans;

import com.assertsl.workshop.dto.DrugDto;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class TransformationBean {

    public Map getDrugParameters(@Header("ncdCode") String ncdCode) {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("productNdc", ncdCode);
        return queryParameters;
    }


    public Map updateDrugParameters(DrugDto drugDto) {
        Map<String, Object> updateParameters = new HashMap<String, Object>();
        
        //TODO: (OK) set parameters from dto and update the entity
        String ndc = drugDto.getProductNdc();
        BigDecimal price = drugDto.getPrice();
        int existences = drugDto.getExistences();
        
        updateParameters.put("productNdc", ndc);
        updateParameters.put("price", price);
        updateParameters.put("existences", existences);        
        
        return updateParameters;
    }

    public void readContentRequest(Exchange exchange) throws IOException, MessagingException {
        InputStream is = exchange.getIn().getBody(InputStream.class);
        MimeBodyPart mimeMessage = new MimeBodyPart(is);
        DataHandler dh = mimeMessage.getDataHandler();
        exchange.getIn().setBody(dh.getInputStream());
        exchange.getIn().setHeader(Exchange.FILE_NAME, dh.getName());
    }


}
