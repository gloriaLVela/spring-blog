package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class ShopController {

    /**
     * Create an HashMap<String, Boolean> called items and create at least 6 items on it,
     * where the String is the name of the item and the Boolean indicates if it's on sale.
     */

    /**
     * /shop.
     * <p>
     * /shop?order=true.
     * The controller should show all the items in a HTML list (OL) format ordered by their name.
     * You can read about sorting Collections here: https://www.baeldung.com/java-hashmap-sort
     */


    private HashMap<String, Boolean> createData() {
        HashMap<String, Boolean> items = new HashMap<>();
        // and putting some data into it
        items.put("hammer", true);
        items.put("spoon", false);
        items.put("table", false);
        items.put("rocking chair", true);
        items.put("iron hammer", true);
        items.put("plastic spoon", false);
        items.put("tabletop", false);
        items.put("blue chair", true);

        return items;
    }

    /**
     * The controller should show all the items in a HTML list (UL) format.
     */
    @RequestMapping(path = "/items/display", method = RequestMethod.GET)
    @ResponseBody
    public String all() {

        HashMap<String, Boolean> items = createData();

        // Getting an iterator
        Iterator hmIterator = items.entrySet().iterator();

        // Iterate through the hash map
        // and add some bonus marks for every student
        // System.out.println("HashMap after adding bonus marks:");

        String html = "<ul>";

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            boolean marks = ((boolean) mapElement.getValue());
            System.out.println(mapElement.getKey() + " : " + marks);
            html += "<li>" + mapElement.getKey() + "</li>";
        }

        html += "</ul>";

        return html;
    }

    /** shop?sale=true. The controller should show items on sale in a HTML list (OL) format.
     **/
    @RequestMapping(path = "/shop", method = RequestMethod.GET)
    @ResponseBody
    public String itemsSale(@RequestParam boolean sale) {

        HashMap<String, Boolean> items = createData();

        // Getting an iterator
        Iterator hmIterator = items.entrySet().iterator();

        System.out.println("sale = " + sale);
        // Iterate through the hash map
        // and add some bonus marks for every student
        // System.out.println("HashMap after adding bonus marks:");

        String html = "<ol>";

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            boolean marks = ((boolean) mapElement.getValue());
            System.out.println(mapElement.getKey() + " : " + marks);
            if (marks == sale) {
                html += "<li>" + mapElement.getKey() + "</li>";
            }
        }

        html += "</ol>";

        return html;
    }

    /** shop?sale=true. The controller should show items on sale in a HTML list (OL) format.
     **/
    @RequestMapping(path = "/shop/order", method = RequestMethod.GET)
    @ResponseBody
    public String itemsOrder(@RequestParam boolean order) {

        HashMap<String, Boolean> items = createData();

        // Getting an iterator
        Iterator hmIterator = items.entrySet().iterator();

        System.out.println("sale = " + order);

        String html = "<ol>";

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            boolean marks = ((boolean) mapElement.getValue());
            System.out.println(mapElement.getKey() + " : " + marks);
                html += "<li>" + mapElement.getKey() + "</li>";
        }

        html += "</ol>";

        return html;
    }


}
