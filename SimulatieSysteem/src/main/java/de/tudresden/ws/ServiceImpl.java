/*   
    Copyright (C) 2016 Mario Krumnow, Dresden University of Technology

    This file is part of TraaS.

    TraaS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.

    TraaS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TraaS.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.tudresden.ws;

import de.tudresden.sumo.util.ConvertHelper;
import de.tudresden.sumo.util.Sumo;
import de.tudresden.ws.conf.Config;
import de.tudresden.ws.log.Log;

/**
 *
 * @author Mario Krumnow
 *
 */
public class ServiceImpl extends Traci implements Service {

    Log logger;
    Config conf;
    Sumo sumo;
    ConvertHelper helper;

    public ServiceImpl(Config conf) {
        this.conf = conf;
        this.logger = conf.logger;
        this.conf.refresh_actiontime();
        this.helper = new ConvertHelper(this.logger);
    }

    public String start(String user) {

        String output = "failed";

        if (!this.conf.running) {
            conf.running = true;
            logger.write("Benutzer " + user + " startet den " + conf.name + " Service", 1);

            sumo = new Sumo(this.conf);
            sumo.start_ws();

            //init super class
            super.init(sumo, logger, helper);

            this.conf.refresh_actiontime();
            output = "success";
        }

        return output;

    }

    public String stop(String user) {

        sumo.stop_instance();
        conf.running = false;
        return "success";

    }

    public String get_Status(String user) {
        this.conf.refresh_actiontime();
        return "Running: " + conf.running;
    }

    public String LastActionTime() {
        return conf.get_actiontime();
    }

    public String version() {
        return conf.version;
    }

    public String TXT_output(boolean input) {
        logger.txt_output(input);
        return "success";
    }

    public void addOption(String name, String value) {

        if (!conf.running) {
            this.conf.sumo_output.put(name, value);
        }

    }

    public void doTimestep() {

        if (conf.running) {
            this.sumo.do_timestep();
        }

    }

    @SuppressWarnings("static-access")
    public void setConfig(String filename) {

        if (!this.conf.running) {
            this.conf.config_file = filename;
        }

    }

    @SuppressWarnings("static-access")
    public void setSumoBinary(String filename) {

        if (!this.conf.running) {
            this.conf.sumo_bin = filename;
        }

    }

}
