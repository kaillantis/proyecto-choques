package impactTest;

import java.awt.Color;
import java.io.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.stevesoft.pat.Regex;

public class PuntoMesh {

  public float x,y,z,vx,vy,vz,x2,y2,z2;
  public Color color;
  public boolean selected = false;
  private Object[] arr = new Object[2];
  private PuntoMesh tx;
private boolean processed;
private boolean add;
  

  public PuntoMesh(float x3, float y3, float z3) {
	// TODO Auto-generated constructor stub
  }

  public void deselectRequiredObjects() {
      // Do nothing
  }

  public void reset(boolean do_mesh) {
      
      x2 = x+vx; y2 = y+vy; z2 = z+vz;
      
  };
  public void mesh(int type, float size){}
  public boolean delete(){return selected;}

  public PuntoMesh(boolean add) {
    this(0,0,0,0,0,0,Color.black,add);
  }

  public PuntoMesh(int i, int j, int k, int l, int m, int n, Color black,
		boolean add) {
	// TODO Auto-generated constructor stub
}

public PuntoMesh(PuntoMesh p) {
      this(p.x,p.y,p.z,p.vx,p.vy,p.vz,p.color,false);
    }

public PuntoMesh(float xx, float yy, float zz, float vx, float vy, float vz, Color cl,boolean add) {
    this.add = add;
    x=xx;
    y=yy;
    z=zz;
    this.vx = vx;
    this.vy = vy;
    this.vz = vz;
    color=cl;
  }

  public PuntoMesh(float xx, float yy, float zz, float vx, float vy, float vz, Color cl) {
      this(xx,yy,zz,vx,vy,vz,cl,false);
    }

  public PuntoMesh(float xx, float yy, float zz, float vx, float vy, float vz) {
      this(xx,yy,zz,vx,vy,vz,Color.black,false);
    }

  public PuntoMesh(float xx, float yy, float zz, Color cl) {
      this(xx,yy,zz,0f,0f,0f,cl,false);
    }


  public boolean isSelected(){return selected;}
  public void setSelected(boolean sel){ selected=sel; }
  public MutableTreeNode get_TreeNode(){
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
    return node;
  }
  
  public void transform3D(Matrix3D t){
    if(!selected || processed)return;
    float xx=x*t.xx+y*t.xy+z*t.xz+t.xo;
    float yy=x*t.yx+y*t.yy+z*t.yz+t.yo;
    float zz=x*t.zx+y*t.zy+z*t.zz+t.zo;
    x=xx; y=yy; z=zz;
    xx=x2*t.xx+y2*t.xy+z2*t.xz+t.xo;
    yy=x2*t.yx+y2*t.yy+z2*t.yz+t.yo;
    zz=x2*t.zx+y2*t.zy+z2*t.zz+t.zo;
    x2=xx; y2=yy; z2=zz;
  }
  
 
  public void requestFocus() {

      tx.requestFocus();
      tx.selectAll();

  }

  private void selectAll() {
	// TODO Auto-generated method stub
	
}

  public Nodo[] get_Nodes(){return null;}
  public float distance(PuntoMesh p){ return (float)Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y)+(z-p.z)*(z-p.z)); }

  public Vector3D getVector() {
      Vector3D v = new Vector3D();

      v.x = this.x;
      v.y = this.y;
      v.z = this.z;

      return v;
  }

  public void toLocal(Vector3D translate, Matrix3D rotate) {

      Vector3D temp = new Vector3D(this.x, this.y, this.z);

      temp.add(temp,translate);
      temp.mult(rotate,temp);

      this.x = temp.x;
      this.y = temp.y;
      this.z = temp.z;

  }

  public void toGlobal(Vector3D translate, Matrix3D rotate) {

      Vector3D temp = new Vector3D(this.x, this.y, this.z);

      temp.sub(temp,translate);
      temp.transMult(rotate,temp);

      this.x = temp.x;
      this.y = temp.y;
      this.z = temp.z;

  }



  public Vector3D getCenter() {
      Vector3D s = new Vector3D(x,y,z);

      return s;
  }


  
}

