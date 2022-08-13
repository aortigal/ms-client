package com.bank.msclient.models.documents;

import com.bank.msclient.models.utils.Audit;
import lombok.Data;

import java.util.List;

@Data
public class Personal extends Audit
{
    public String id;
    private String savingAccount;
    private String currentAccount;
    private List<String> fixedTermAccounts;
}
