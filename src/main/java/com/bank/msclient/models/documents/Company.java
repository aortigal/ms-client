package com.bank.msclient.models.documents;

import com.bank.msclient.models.utils.Audit;
import lombok.Data;

import java.util.List;

@Data
public class Company extends Audit
{
    public String id;
    public List<String> owners;
    public List<String> signatories;
    public List<String> currentAccounts;
}
