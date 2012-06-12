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


/**
 * Insert the type's description here. Creation date: (28/08/01 %T)
 *
 * @author: Jonas Forssell, Yuriy Mikhaylovskiy
 */
public class Load implements java.io.Serializable{
    public java.lang.String name;
    private Matrix load;
    private Matrix acc;
    private boolean x_force_is_set = false;
    private boolean y_force_is_set = false;
    private boolean z_force_is_set = false;
    private boolean z_moment_is_set = false;
    private boolean y_moment_is_set = false;
    private boolean x_moment_is_set = false;
    private boolean x_acc_is_set = false;
    private boolean z_acc_is_set = false;
    private boolean y_acc_is_set = false;
    private boolean x_rot_acc_is_set = false;
    private boolean z_rot_acc_is_set = false;
    private boolean y_rot_acc_is_set = false;
    private boolean pressure_is_set = false;
    private Variable x_force;
    private Variable y_force;
    private Variable z_force;
    private Variable x_moment;
    private Variable y_moment;
    private Variable z_moment;
    private Variable x_acc;
    private Variable y_acc;
    private Variable z_acc;
    private Variable x_rot_acc;
    private Variable y_rot_acc;
    private Variable z_rot_acc;
    private Variable pressure;

    /**
     * When a load is initialized, the default setting is 0 on everything. Any
     * changes will be applied by the initialization object.
     */
    public Load() {
        int i;
        load = new Matrix(6, 1);
        acc = new Matrix(6, 1);

        for (i = 0; i < 6; i++) {
            load.set(i, 0, 0);
            acc.set(i, 0, 0);
        }
    }

    /**
     * This method returns the force matrix (fx fy fz mx my mz)T Creation date:
     * (19/11/01 %T)
     *
     * @return Jama.Matrix
     */
    public Matrix getLoad(double currtime) {
        // Update the matrix
        if (x_force_is_set) {
            load.set(
                0, 0,
                x_force.value(currtime) * (x_force.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (y_force_is_set) {
            load.set(
                1, 0,
                y_force.value(currtime) * (y_force.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (z_force_is_set) {
            load.set(
                2, 0,
                z_force.value(currtime) * (z_force.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (x_moment_is_set) {
            load.set(
                3, 0,
                x_moment.value(currtime) * (x_moment.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (y_moment_is_set) {
            load.set(
                4, 0,
                y_moment.value(currtime) * (y_moment.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (z_moment_is_set) {
            load.set(
                5, 0,
                z_moment.value(currtime) * (z_moment.on(currtime) ? 1.0 : 0.0)
            );
        }

        // return it
        return load;
    }

    /**
     * This method returns the acceleration matrix (ax ay az arx ary arz)T
     * Creation date: (19/11/01 %T)
     *
     * @return Jama.Matrix
     */
    public Matrix getAcc(double currtime) {
        // Update the matrix
        if (x_acc_is_set) {
            acc.set(
                0, 0, x_acc.value(currtime) * (x_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (y_acc_is_set) {
            acc.set(
                1, 0, y_acc.value(currtime) * (y_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (z_acc_is_set) {
            acc.set(
                2, 0, z_acc.value(currtime) * (z_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (x_rot_acc_is_set) {
            acc.set(
                3, 0,
                x_rot_acc.value(currtime) * (x_rot_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (y_rot_acc_is_set) {
            acc.set(
                4, 0,
                y_rot_acc.value(currtime) * (y_rot_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        if (z_rot_acc_is_set) {
            acc.set(
                5, 0,
                z_rot_acc.value(currtime) * (z_rot_acc.on(currtime) ? 1.0 : 0.0)
            );
        }

        // return it
        return acc;
    }

    /**
     * This method returns the pressure Creation date: (19/11/01 %T)
     *
     * @return Jama.Matrix
     */
    public double getPressure(double currtime) {
        // Update the pressure and return it
        if (pressure_is_set) {
            return pressure.value(currtime) * (pressure.on(currtime) ? 1.0 : 0.0);
        }

        // Otherwise, return zero
        return 0.0;
    }

    /**
     * This method is used to check that all mandatory parameters have been set
     */
    public void checkIndata()
        throws IllegalArgumentException
    {
    }
	/**
	 * Returns the name.
	 * @return java.lang.String
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

}

