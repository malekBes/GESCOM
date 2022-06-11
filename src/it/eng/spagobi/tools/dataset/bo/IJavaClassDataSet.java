package it.eng.spagobi.tools.dataset.bo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
/**
 *
 * @author rss
 */
import java.util.List;
import java.util.Map;

public interface IJavaClassDataSet {

    /**
     * Gets the values formatted into an xml structure.
     *
     * @param profile a user profile used to fill attributes required by the
     * query
     * @param parameters
     *
     * @return the xml string of the values
     */
    public String getValues(Map profile, Map parameters);

    /**
     * Gets the list of profile attribute names required by the class.
     *
     * @return the list of profile attribute names
     */
    public List getNamesOfProfileAttributeRequired();

}
