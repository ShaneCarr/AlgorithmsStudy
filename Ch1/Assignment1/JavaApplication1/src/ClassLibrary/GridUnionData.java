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
public class GridUnionData {
    public SiteId ParentId;
    public SiteId Id;
    public SiteState State;
    
   @Override
   public String toString()
   {
       return "Row: " + Id.Row + " Column: " + Id.Column + " SiteState: " + this.State.toString();
   }
}
