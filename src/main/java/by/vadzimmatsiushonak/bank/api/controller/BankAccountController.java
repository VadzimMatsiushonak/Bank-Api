package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankAccountMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankAccountRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @GetMapping("/{id}")
    public BankAccountDtoRelations findById(@PathVariable Long id) {
        return bankAccountMapper.toDtoRelations(bankAccountService.findById(id).orElse(null));
    }

    @GetMapping
    public List<BankAccountDtoRelations> findAll() {
        return bankAccountMapper.toListDtoRelations(bankAccountService.findAll());
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccountRequestDto bankAccountRequestDto) {
        return bankAccountService.create(bankAccountMapper.toEntity(bankAccountRequestDto));
    }
}
