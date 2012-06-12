/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOUSE. See the GNU
 * General Public License for more details.
 *
 * You should have recieved a copy of the GNU General Public License
 * along with this program; if not write to the Free Software
 * Foundation, inc., 59 Temple Place, Suite 330, Boston MA 02111-1307
 * USA
 */
package impactTest;

import java.awt.*;
import javax.swing.tree.*;
import javax.swing.SpringLayout.Constraints;

public class Nodo{
	
  public float x,y,z;
  public Color color;
  public Constraints constraint;
  public Loads load;
  public Float M,Ixx,Iyy,Izz,Ixy,Iyz,Ixz;
  private Nodo merged_reference;
  //private Object[] arr;

   public Nodo(float xx, float yy, float zz) {
    this(xx,yy,zz,Color.black);
  }

  public Nodo(float xx, float yy, float zz, Color cl, Constraints con, Loads ld) {
    x=xx;
    y=yy;
    z=zz;
    color=cl;
    constraint=con;
    load=ld;
  }

 
  public Nodo(float xx, float yy, float zz, Color black) {
	  this.x = xx;
	  this.y = yy;
	  this.z = zz;
	  this.color = black;
  }


  public void reset(boolean do_mesh) {};
  
  public void mesh(int type, float size){}
  
  
  public MutableTreeNode get_TreeNode(){
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
    return node;
  }

  public void transform3D(Matrix3D t){
    float xx=x*t.xx+y*t.xy+z*t.xz+t.xo;
    float yy=x*t.yx+y*t.yy+z*t.yz+t.yo;
    float zz=x*t.zx+y*t.zy+z*t.zz+t.zo;
    x=xx; y=yy; z=zz;
  }

  public Nodo[] get_Nodes(){
    Nodo[] arr = {this};
    return arr;
  }

  public Nodo getMergedReference() {
    return merged_reference;
  }
  
  public void setMergedReference(Nodo merged_reference) {
	  this.merged_reference = merged_reference;
  }

  public Vector3D getCenter() {
    Vector3D s = new Vector3D(x,y,z);

    return s;
  }

}