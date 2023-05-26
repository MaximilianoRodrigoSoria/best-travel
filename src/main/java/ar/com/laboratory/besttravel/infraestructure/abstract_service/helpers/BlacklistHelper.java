package ar.com.laboratory.besttravel.infraestructure.abstract_service.helpers;

import ar.com.laboratory.besttravel.util.exceptions.ForbiddenCustomerException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BlacklistHelper {

    public void isInBlacklist(String customerId){
        if(customerId.equals("GOTW771012HMRGR087")){
            throw  new ForbiddenCustomerException();
        }
    }

}
