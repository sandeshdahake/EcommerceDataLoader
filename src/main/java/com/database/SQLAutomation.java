package com.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Controller
public class SQLAutomation {
    @Autowired
    SqlAutomationRepository sqlAutomationRepository;
    public void runSP(){

    }
}
