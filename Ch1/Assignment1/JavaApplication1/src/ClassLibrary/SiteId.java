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
public class SiteId {
    
    public SiteId(int row, int column)
    {
        this.Row = row;
        this.Column = column;
    }
    
    public int Row;
    public int Column;
    
    public boolean Equals(SiteId id)
    {
        return this.Row == id.Row && this.Column == id.Column;
    }
}
