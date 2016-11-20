/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

/**
 *
 * @author shane
 */
public class RootResult {
    public RootResult(SiteId item, int dept)
    {
        this.dataItem = item;
        this.DepthToCurrent = dept;
    }
    
    public SiteId dataItem;
    public int DepthToCurrent;
}
