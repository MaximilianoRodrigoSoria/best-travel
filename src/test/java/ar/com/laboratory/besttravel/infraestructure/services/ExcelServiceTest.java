package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.domain.repositories.jpa.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExcelService.class})
@ExtendWith(SpringExtension.class)
class ExcelServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private ExcelService excelService;

    /**
     * Method under test: {@link ExcelService#readFile()}
     */
    @Test
    void testReadFile() {
        when(customerRepository.findAll()).thenThrow(new RuntimeException("U/U"));
        assertThrows(RuntimeException.class, () -> excelService.readFile());
        verify(customerRepository).findAll();
    }
}

