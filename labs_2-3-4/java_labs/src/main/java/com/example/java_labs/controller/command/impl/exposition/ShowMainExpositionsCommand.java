package com.example.java_labs.controller.command.impl.exposition;

import com.example.java_labs.controller.command.Command;
import com.example.java_labs.controller.command.constants.ShowBy;
import com.example.java_labs.controller.services.ExpositionService;
import com.example.java_labs.controller.util.constants.ViewJsp;
import com.example.java_labs.domain.Exposition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowMainExpositionsCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(ShowMainExpositionsCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String showByName = request.getParameter("showBy");
        String topic = request.getParameter("topic");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        String date = request.getParameter("date");

        ShowBy showBy = showByName == null || showByName.equals("") ? null : ShowBy.valueOf(showByName);
        Double priceFromDouble = priceFrom == null || priceFrom.equals("") ? 0 : Double.parseDouble(priceFrom);
        Double priceToDouble = priceTo == null || priceTo.equals("") ? Double.MAX_VALUE : Double.parseDouble(priceTo);
        Date date1 = null;
        try {
            date1 = date == null || date.equals("") ? null : new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Exposition> expositions = ExpositionService.getInstance().getExpositions(
                showBy,
                topic,
                priceFromDouble,
                priceToDouble,
                date1
        );

        request.getSession().setAttribute("expositions", expositions);
        logger.info("Successfully loaded expositions for user");
        return ViewJsp.INDEX_JSP.getPath();
    }
}
