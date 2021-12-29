package com.example.java_labs.controller.command.constants;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.impl.authorization.LoginCommand;
import com.example.java_labs.controller.command.impl.authorization.LogOutCommand;
import com.example.java_labs.controller.command.impl.authorization.RegisterCommand;
import com.example.java_labs.controller.command.impl.exposition.*;
import com.example.java_labs.controller.command.impl.hall.CreateHallCommand;
import com.example.java_labs.controller.command.impl.hall.ShowCreateHallPageCommand;
import com.example.java_labs.controller.command.impl.ticket.BuyTicketCommand;
import com.example.java_labs.controller.command.impl.ticket.ShowTicketsCommand;

public enum Commands {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogOutCommand()),
    SHOW_MAIN_EXPOSITIONS(new ShowMainExpositionsCommand()),
    SHOW_ADMIN_EXPOSITIONS(new ShowAdminExpositionsCommand()),
    SHOW_ADMIN_CREATE_HALL(new ShowCreateHallPageCommand()),
    CREATE_HALL(new CreateHallCommand()),
    SHOW_ADMIN_CREATE_EXPOSITION(new ShowCreateExpositionPageCommand()),
    CREATE_EXPOSITION(new CreateExpositionCommand()),
    CANCEL_EXPOSITION(new CancelExpositionCommand()),
    SHOW_STATISTICS(new ShowStatisticsCommand()),
    BUY_TICKET(new BuyTicketCommand()),
    SHOW_TICKETS(new ShowTicketsCommand()),
    SHOW_EXPOSITION_DETAILS(new ShowExpositionDetailsCommand());

    private Command command;

    Commands(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
