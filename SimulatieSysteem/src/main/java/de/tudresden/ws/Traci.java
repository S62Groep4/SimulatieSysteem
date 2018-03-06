/*   
    Copyright (C) 2017 Mario Krumnow, Dresden University of Technology

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

import de.tudresden.sumo.cmd.Edge;
import de.tudresden.sumo.cmd.Gui;
import de.tudresden.sumo.cmd.Inductionloop;
import de.tudresden.sumo.cmd.Junction;
import de.tudresden.sumo.cmd.Lanearea;
import de.tudresden.sumo.cmd.Lane;
import de.tudresden.sumo.cmd.Multientryexit;
import de.tudresden.sumo.cmd.Person;
import de.tudresden.sumo.cmd.Poi;
import de.tudresden.sumo.cmd.Polygon;
import de.tudresden.sumo.cmd.Route;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Vehicletype;
import de.tudresden.ws.container.SumoBoundingBox;
import de.tudresden.ws.container.SumoColor;
import de.tudresden.ws.container.SumoGeometry;
import de.tudresden.ws.container.SumoLinkList;
import de.tudresden.ws.container.SumoPosition2D;
import de.tudresden.ws.container.SumoPosition3D;
import de.tudresden.ws.container.SumoStopFlags;
import de.tudresden.ws.container.SumoStringList;
import de.tudresden.ws.container.SumoTLSProgram;
import de.tudresden.ws.container.SumoTLSController;
import de.tudresden.ws.container.SumoVehicleData;
import de.tudresden.ws.log.Log;
import de.tudresden.sumo.util.ConvertHelper;
import de.tudresden.sumo.util.Sumo;

public class Traci {

    Sumo sumo;
    Log logger;
    ConvertHelper helper;

    public void init(Sumo sumo, Log logger, ConvertHelper helper) {
        this.sumo = sumo;
        this.logger = logger;
        this.helper = helper;
    }

    /*
	 * Setter methods
     */
    public void Edge_adaptTraveltime(String edgeID, int time) {
        this.sumo.set_cmd(Edge.adaptTraveltime(edgeID, time));
    }

    public void Edge_setEffort(String edgeID, double effort) {
        this.sumo.set_cmd(Edge.setEffort(edgeID, effort));
    }

    public void Edge_setMaxSpeed(String edgeID, double speed) {
        this.sumo.set_cmd(Edge.setMaxSpeed(edgeID, speed));
    }

    public void Vehicle_add(String vehID, String typeID, String routeID, int depart, double pos, double speed, byte lane) {
        this.sumo.set_cmd(Vehicle.add(vehID, typeID, routeID, depart, pos, speed, lane));
    }

    public void Vehicle_changeLane(String vehID, byte laneIndex, int duration) {
        this.sumo.set_cmd(Vehicle.changeLane(vehID, laneIndex, duration));
    }

    public void Vehicle_changeTarget(String vehID, String edgeID) {
        this.sumo.set_cmd(Vehicle.changeTarget(vehID, edgeID));
    }

    public void Vehicle_moveTo(String vehID, String laneID, double pos) {
        this.sumo.set_cmd(Vehicle.moveTo(vehID, laneID, pos));
    }

    public void Vehicle_remove(String vehID, byte reason) {
        this.sumo.set_cmd(Vehicle.remove(vehID, reason));
    }

    public void Vehicle_rerouteEffort(String vehID) {
        this.sumo.set_cmd(Vehicle.rerouteEffort(vehID));
    }

    public void Vehicle_rerouteTraveltime(String vehID) {
        this.sumo.set_cmd(Vehicle.rerouteTraveltime(vehID));
    }

    public void Vehicle_setAccel(String vehID, double accel) {
        this.sumo.set_cmd(Vehicle.setAccel(vehID, accel));
    }

    public void Vehicle_setAdaptedTraveltime(String vehID, int begTime, int endTime, String edgeID, double time) {
        this.sumo.set_cmd(Vehicle.setAdaptedTraveltime(vehID, begTime, endTime, edgeID, time));
    }

    public void Vehicle_setColor(String vehID, SumoColor color) {
        this.sumo.set_cmd(Vehicle.setColor(vehID, color));
    }

    public void Vehicle_setDecel(String vehID, double decel) {
        this.sumo.set_cmd(Vehicle.setDecel(vehID, decel));
    }

    public void Vehicle_setEffort(String vehID, int begTime, int endTime, String edgeID, double effort) {
        this.sumo.set_cmd(Vehicle.setEffort(vehID, begTime, endTime, edgeID, effort));
    }

    public void Vehicle_setEmissionClass(String vehID, String clazz) {
        this.sumo.set_cmd(Vehicle.setEmissionClass(vehID, clazz));
    }

    public void Vehicle_setImperfection(String vehID, double imperfection) {
        this.sumo.set_cmd(Vehicle.setImperfection(vehID, imperfection));
    }

    public void Vehicle_setLength(String vehID, double length) {
        this.sumo.set_cmd(Vehicle.setLength(vehID, length));
    }

    public void Vehicle_setMaxSpeed(String vehID, double speed) {
        this.sumo.set_cmd(Vehicle.setMaxSpeed(vehID, speed));
    }

    public void Vehicle_setMinGap(String vehID, double minGap) {
        this.sumo.set_cmd(Vehicle.setMinGap(vehID, minGap));
    }

    public void Vehicle_setRouteID(String vehID, String routeID) {
        this.sumo.set_cmd(Vehicle.setRouteID(vehID, routeID));
    }

    public void Vehicle_setShapeClass(String vehID, String clazz) {
        this.sumo.set_cmd(Vehicle.setShapeClass(vehID, clazz));
    }

    public void Vehicle_setSignals(String vehID, int signals) {
        this.sumo.set_cmd(Vehicle.setSignals(vehID, signals));
    }

    public void Vehicle_setSpeed(String vehID, double speed) {
        this.sumo.set_cmd(Vehicle.setSpeed(vehID, speed));
    }

    public void Vehicle_setSpeedDeviation(String vehID, double deviation) {
        this.sumo.set_cmd(Vehicle.setSpeedDeviation(vehID, deviation));
    }

    public void Vehicle_setSpeedFactor(String vehID, double factor) {
        this.sumo.set_cmd(Vehicle.setSpeedFactor(vehID, factor));
    }

    public void Vehicle_setStop(String vehID, String edgeID, double pos, byte laneIndex, int duration, SumoStopFlags stopType) {
        this.sumo.set_cmd(Vehicle.setStop(vehID, edgeID, pos, laneIndex, duration, stopType));
    }

    public void Vehicle_resume(String vehID) {
        this.sumo.set_cmd(Vehicle.resume(vehID));
    }

    public void Vehicle_setTau(String vehID, double tau) {
        this.sumo.set_cmd(Vehicle.setTau(vehID, tau));
    }

    public void Vehicle_setVehicleClass(String vehID, String clazz) {
        this.sumo.set_cmd(Vehicle.setVehicleClass(vehID, clazz));
    }

    public void Vehicle_setWidth(String vehID, double width) {
        this.sumo.set_cmd(Vehicle.setWidth(vehID, width));
    }

    public void Trafficlights_setCompleteRedYellowGreenDefinition(String tlsID, SumoTLSProgram tls) {
        this.sumo.set_cmd(Trafficlight.setCompleteRedYellowGreenDefinition(tlsID, tls));
    }

    public void Trafficlights_setPhase(String tlsID, int index) {
        this.sumo.set_cmd(Trafficlight.setPhase(tlsID, index));
    }

    public void Trafficlights_setPhaseDuration(String tlsID, int phaseDuration) {
        this.sumo.set_cmd(Trafficlight.setPhaseDuration(tlsID, phaseDuration));
    }

    public void Trafficlights_setProgram(String tlsID, String programID) {
        this.sumo.set_cmd(Trafficlight.setProgram(tlsID, programID));
    }

    public void Trafficlights_setRedYellowGreenState(String tlsID, String state) {
        this.sumo.set_cmd(Trafficlight.setRedYellowGreenState(tlsID, state));
    }

    public void Vehicletype_setAccel(String typeID, double accel) {
        this.sumo.set_cmd(Vehicletype.setAccel(typeID, accel));
    }

    public void Vehicletype_setColor(String typeID, SumoColor color) {
        this.sumo.set_cmd(Vehicletype.setColor(typeID, color));
    }

    public void Vehicletype_setDecel(String typeID, double decel) {
        this.sumo.set_cmd(Vehicletype.setDecel(typeID, decel));
    }

    public void Vehicletype_setEmissionClass(String typeID, String clazz) {
        this.sumo.set_cmd(Vehicletype.setEmissionClass(typeID, clazz));
    }

    public void Vehicletype_setImperfection(String typeID, double imperfection) {
        this.sumo.set_cmd(Vehicletype.setImperfection(typeID, imperfection));
    }

    public void Vehicletype_setLength(String typeID, double length) {
        this.sumo.set_cmd(Vehicletype.setLength(typeID, length));
    }

    public void Vehicletype_setMaxSpeed(String typeID, double speed) {
        this.sumo.set_cmd(Vehicletype.setMaxSpeed(typeID, speed));
    }

    public void Vehicletype_setMinGap(String typeID, double minGap) {
        this.sumo.set_cmd(Vehicletype.setMinGap(typeID, minGap));
    }

    public void Vehicletype_setShapeClass(String typeID, String clazz) {
        this.sumo.set_cmd(Vehicletype.setShapeClass(typeID, clazz));
    }

    public void Vehicletype_setSpeedDeviation(String typeID, double deviation) {
        this.sumo.set_cmd(Vehicletype.setSpeedDeviation(typeID, deviation));
    }

    public void Vehicletype_setSpeedFactor(String typeID, double factor) {
        this.sumo.set_cmd(Vehicletype.setSpeedFactor(typeID, factor));
    }

    public void Vehicletype_setTau(String typeID, double tau) {
        this.sumo.set_cmd(Vehicletype.setTau(typeID, tau));
    }

    public void Vehicletype_setVehicleClass(String typeID, String clazz) {
        this.sumo.set_cmd(Vehicletype.setVehicleClass(typeID, clazz));
    }

    public void Vehicletype_setWidth(String typeID, double width) {
        this.sumo.set_cmd(Vehicletype.setWidth(typeID, width));
    }

    public void Lane_setAllowed(String laneID, SumoStringList allowedClasses) {
        this.sumo.set_cmd(Lane.setAllowed(laneID, allowedClasses));
    }

    public void Lane_setDisallowed(String laneID, SumoStringList disallowedClasses) {
        this.sumo.set_cmd(Lane.setDisallowed(laneID, disallowedClasses));
    }

    public void Lane_setLength(String laneID, double length) {
        this.sumo.set_cmd(Lane.setLength(laneID, length));
    }

    public void Lane_setMaxSpeed(String laneID, double speed) {
        this.sumo.set_cmd(Lane.setMaxSpeed(laneID, speed));
    }

    public void Polygon_add(String polygonID, SumoGeometry shape, SumoColor color, boolean fill, String polygonType, int layer) {
        this.sumo.set_cmd(Polygon.add(polygonID, shape, color, fill, polygonType, layer));
    }

    public void Polygon_remove(String polygonID, int layer) {
        this.sumo.set_cmd(Polygon.remove(polygonID, layer));
    }

    public void Polygon_setColor(String polygonID, SumoColor color) {
        this.sumo.set_cmd(Polygon.setColor(polygonID, color));
    }

    public void Polygon_setShape(String polygonID, SumoStringList shape) {
        this.sumo.set_cmd(Polygon.setShape(polygonID, shape));
    }

    public void Polygon_setType(String polygonID, String polygonType) {
        this.sumo.set_cmd(Polygon.setType(polygonID, polygonType));
    }

    public void Poi_remove(String poiID, int layer) {
        this.sumo.set_cmd(Poi.remove(poiID, layer));
    }

    public void Poi_setColor(String poiID, SumoColor color) {
        this.sumo.set_cmd(Poi.setColor(poiID, color));
    }

    public void Poi_setPosition(String poiID, double x, double y) {
        this.sumo.set_cmd(Poi.setPosition(poiID, x, y));
    }

    public void Poi_setType(String poiID, String poiType) {
        this.sumo.set_cmd(Poi.setType(poiID, poiType));
    }

    public void GUI_screenshot(String viewID, String filename) {
        this.sumo.set_cmd(Gui.screenshot(viewID, filename));
    }

    public void GUI_setBoundary(String viewID, double xmin, double ymin, double xmax, double ymax) {
        this.sumo.set_cmd(Gui.setBoundary(viewID, xmin, ymin, xmax, ymax));
    }

    public void GUI_setOffset(String viewID, double x, double y) {
        this.sumo.set_cmd(Gui.setOffset(viewID, x, y));
    }

    public void GUI_setSchema(String viewID, String schemeName) {
        this.sumo.set_cmd(Gui.setSchema(viewID, schemeName));
    }

    public void GUI_setZoom(String viewID, double zoom) {
        this.sumo.set_cmd(Gui.setZoom(viewID, zoom));
    }

    public void GUI_trackVehicle(String viewID, String vehID) {
        this.sumo.set_cmd(Gui.trackVehicle(viewID, vehID));
    }

    public void Route_add(String routeID, SumoStringList edges) {
        this.sumo.set_cmd(Route.add(routeID, edges));
    }

    /*
	 * Getter methods
     */
    public SumoStringList Multientryexit_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Multientryexit.getIDList()));
    }

    public int Multientryexit_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Multientryexit.getIDCount()));
    }

    public int Multientryexit_getLastStepHaltingNumber(String detID) {
        return this.helper.getInt(this.sumo.get_cmd(Multientryexit.getLastStepHaltingNumber(detID)));
    }

    public double Multientryexit_getLastStepMeanSpeed(String detID) {
        return this.helper.getDouble(this.sumo.get_cmd(Multientryexit.getLastStepMeanSpeed(detID)));
    }

    public SumoStringList Multientryexit_getLastStepVehicleIDs(String detID) {
        return this.helper.getStringList(this.sumo.get_cmd(Multientryexit.getLastStepVehicleIDs(detID)));
    }

    public int Multientryexit_getLastStepVehicleNumber(String detID) {
        return this.helper.getInt(this.sumo.get_cmd(Multientryexit.getLastStepVehicleNumber(detID)));
    }

    public double Edge_getAdaptedTraveltime(String edgeID, int time) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getAdaptedTraveltime(edgeID, time)));
    }

    public double Edge_getCO2Emission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getCO2Emission(edgeID)));
    }

    public double Edge_getCOEmission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getCOEmission(edgeID)));
    }

    public double Edge_getEffort(String edgeID, int time) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getEffort(edgeID, time)));
    }

    public double Edge_getElectricityConsumption(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getElectricityConsumption(edgeID)));
    }

    public double Edge_getFuelConsumption(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getFuelConsumption(edgeID)));
    }

    public double Edge_getHCEmission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getHCEmission(edgeID)));
    }

    public int Edge_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Edge.getIDCount()));
    }

    public SumoStringList Edge_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Edge.getIDList()));
    }

    public int Edge_getLastStepHaltingNumber(String edgeID) {
        return this.helper.getInt(this.sumo.get_cmd(Edge.getLastStepHaltingNumber(edgeID)));
    }

    public double Edge_getLastStepLength(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getLastStepLength(edgeID)));
    }

    public double Edge_getLastStepMeanSpeed(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getLastStepMeanSpeed(edgeID)));
    }

    public double Edge_getLastStepOccupancy(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getLastStepOccupancy(edgeID)));
    }

    public SumoStringList Edge_getLastStepVehicleIDs(String edgeID) {
        return this.helper.getStringList(this.sumo.get_cmd(Edge.getLastStepVehicleIDs(edgeID)));
    }

    public int Edge_getLastStepVehicleNumber(String edgeID) {
        return this.helper.getInt(this.sumo.get_cmd(Edge.getLastStepVehicleNumber(edgeID)));
    }

    public double Edge_getNOxEmission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getNOxEmission(edgeID)));
    }

    public double Edge_getNoiseEmission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getNoiseEmission(edgeID)));
    }

    public double Edge_getPMxEmission(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getPMxEmission(edgeID)));
    }

    public double Edge_getTraveltime(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getTraveltime(edgeID)));
    }

    public double Edge_getWaitingTime(String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Edge.getWaitingTime(edgeID)));
    }

    public SumoStringList Lanearea_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Lanearea.getIDList()));
    }

    public int Lanearea_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Lanearea.getIDCount()));
    }

    public int Lanearea_getJamLengthVehicle(String loopID) {
        return this.helper.getInt(this.sumo.get_cmd(Lanearea.getJamLengthVehicle(loopID)));
    }

    public double Lanearea_getJamLengthMeters(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lanearea.getJamLengthMeters(loopID)));
    }

    public double Lanearea_getLastStepMeanSpeed(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lanearea.getLastStepMeanSpeed(loopID)));
    }

    public double Lanearea_getLastStepOccupancy(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lanearea.getLastStepOccupancy(loopID)));
    }

    public SumoStringList Person_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Person.getIDList()));
    }

    public int Person_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Person.getIDCount()));
    }

    public double Person_getSpeed(String personID) {
        return this.helper.getDouble(this.sumo.get_cmd(Person.getSpeed(personID)));
    }

    public SumoPosition2D Person_getPosition(String personID) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Person.getPosition(personID)));
    }

    public SumoPosition3D Person_getPosition3D(String personID) {
        return this.helper.getPosition3D(this.sumo.get_cmd(Person.getPosition3D(personID)));
    }

    public int Person_getAngle(String personID) {
        return this.helper.getInt(this.sumo.get_cmd(Person.getAngle(personID)));
    }

    public String Person_getRoadID(String personID) {
        return this.helper.getString(this.sumo.get_cmd(Person.getRoadID(personID)));
    }

    public String Person_getTypeID(String personID) {
        return this.helper.getString(this.sumo.get_cmd(Person.getTypeID(personID)));
    }

    public double Person_getLanePosition(String personID) {
        return this.helper.getDouble(this.sumo.get_cmd(Person.getLanePosition(personID)));
    }

    public SumoColor Person_getColor(String personID) {
        return this.helper.getColor(this.sumo.get_cmd(Person.getColor(personID)));
    }

    public int Person_getPersonNumber(String personID) {
        return this.helper.getInt(this.sumo.get_cmd(Person.getPersonNumber(personID)));
    }

    public double Person_getLength(String personID) {
        return this.helper.getDouble(this.sumo.get_cmd(Person.getLength(personID)));
    }

    public double Person_getWaitingTime(String personID) {
        return this.helper.getDouble(this.sumo.get_cmd(Person.getWaitingTime(personID)));
    }

    public double Person_getMinGap(String personID) {
        return this.helper.getDouble(this.sumo.get_cmd(Person.getMinGap(personID)));
    }

    public double Vehicle_getAccel(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getAccel(vehID)));
    }

    public double Vehicle_getAdaptedTraveltime(String vehID, int time, String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getAdaptedTraveltime(vehID, time, edgeID)));
    }

    public double Vehicle_getAngle(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getAngle(vehID)));
    }

    public SumoStringList Vehicle_getBestLanes(String vehID) {
        return this.helper.getStringList(this.sumo.get_cmd(Vehicle.getBestLanes(vehID)));
    }

    public double Vehicle_getCO2Emission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getCO2Emission(vehID)));
    }

    public double Vehicle_getCOEmission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getCOEmission(vehID)));
    }

    public SumoColor Vehicle_getColor(String vehID) {
        return this.helper.getColor(this.sumo.get_cmd(Vehicle.getColor(vehID)));
    }

    public double Vehicle_getDecel(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getDecel(vehID)));
    }

    public double Vehicle_getDrivingDistance(String vehID, String edgeID, double pos, byte laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getDrivingDistance(vehID, edgeID, pos, laneID)));
    }

    public double Vehicle_getDrivingDistance2D(String vehID, double x, double y) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getDrivingDistance2D(vehID, x, y)));
    }

    public double Vehicle_getEffort(String vehID, int time, String edgeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getEffort(vehID, time, edgeID)));
    }

    public String Vehicle_getEmissionClass(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getEmissionClass(vehID)));
    }

    public double Vehicle_getFuelConsumption(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getFuelConsumption(vehID)));
    }

    public double Vehicle_getHCEmission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getHCEmission(vehID)));
    }

    public SumoStringList Vehicle_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Vehicle.getIDList()));
    }

    public int Vehicle_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.getIDCount()));
    }

    public double Vehicle_getImperfection(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getImperfection(vehID)));
    }

    public double Vehicle_getAllowedSpeed(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getAllowedSpeed(vehID)));
    }

    public int Vehicle_getPersonNumber(String vehID) {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.getPersonNumber(vehID)));
    }

    public double Vehicle_getDistance(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getDistance(vehID)));
    }

    public double Vehicle_getElectricityConsumption(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getElectricityConsumption(vehID)));
    }

    public double Vehicle_getWaitingTime(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getWaitingTime(vehID)));
    }

    public String Vehicle_getLaneID(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getLaneID(vehID)));
    }

    public int Vehicle_getLaneIndex(String vehID) {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.getLaneIndex(vehID)));
    }

    public double Vehicle_getLanePosition(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getLanePosition(vehID)));
    }

    public String Vehicle_getLeader(String vehID, double dist) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getLeader(vehID, dist)));
    }

    public double Vehicle_getLength(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getLength(vehID)));
    }

    public double Vehicle_getMaxSpeed(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getMaxSpeed(vehID)));
    }

    public double Vehicle_getMinGap(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getMinGap(vehID)));
    }

    public double Vehicle_getNOxEmission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getNOxEmission(vehID)));
    }

    public double Vehicle_getNoiseEmission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getNoiseEmission(vehID)));
    }

    public double Vehicle_getPMxEmission(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getPMxEmission(vehID)));
    }

    public SumoPosition2D Vehicle_getPosition(String vehID) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Vehicle.getPosition(vehID)));
    }

    public SumoPosition3D Vehicle_getPosition3D(String vehID) {
        return this.helper.getPosition3D(this.sumo.get_cmd(Vehicle.getPosition3D(vehID)));
    }

    public String Vehicle_getRoadID(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getRoadID(vehID)));
    }

    public SumoStringList Vehicle_getRoute(String vehID) {
        return this.helper.getStringList(this.sumo.get_cmd(Vehicle.getRoute(vehID)));
    }

    public String Vehicle_getRouteID(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getRouteID(vehID)));
    }

    public String Vehicle_getShapeClass(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getShapeClass(vehID)));
    }

    public int Vehicle_getSignals(String vehID) {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.getSignals(vehID)));
    }

    public double Vehicle_getSlope(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getSlope(vehID)));
    }

    public double Vehicle_getSpeed(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getSpeed(vehID)));
    }

    public double Vehicle_getSpeedDeviation(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getSpeedDeviation(vehID)));
    }

    public double Vehicle_getSpeedFactor(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getSpeedFactor(vehID)));
    }

    public double Vehicle_getSpeedMode(String vehID) {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.getSpeedMode(vehID)));
    }

    public double Vehicle_getSpeedWithoutTraCI(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getSpeedWithoutTraCI(vehID)));
    }

    public double Vehicle_getTau(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getTau(vehID)));
    }

    public String Vehicle_getTypeID(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getTypeID(vehID)));
    }

    public String Vehicle_getVehicleClass(String vehID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicle.getVehicleClass(vehID)));
    }

    public double Vehicle_getWidth(String vehID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicle.getWidth(vehID)));
    }

    public int Vehicle_isRouteValid(String vehID) {
        return this.helper.getInt(this.sumo.get_cmd(Vehicle.isRouteValid(vehID)));
    }

    public void Vehicle_setRoute(String vehID, SumoStringList edgeList) {
        this.sumo.set_cmd(Vehicle.setRoute(vehID, edgeList));
    }

    public void Vehicle_setLaneChangeMode(String vehID, int lcm) {
        this.sumo.set_cmd(Vehicle.setLaneChangeMode(vehID, lcm));
    }

    public void Vehicle_setType(String vehID, String typeID) {
        this.sumo.get_cmd(Vehicle.setType(vehID, typeID));
    }

    public void Vehicle_slowDown(String vehID, double speed, int duration) {
        this.sumo.set_cmd(Vehicle.slowDown(vehID, speed, duration));
    }

    public void GUI_clearPending(String routeID) {
        this.sumo.set_cmd(Simulation.clearPending(routeID));
    }

    public SumoStringList Simulation_convert2D(String edgeID, double pos, byte laneIndex, String toGeo) {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.convert2D(edgeID, pos, laneIndex, toGeo)));
    }

    public SumoStringList Simulation_convert3D(String edgeID, double pos, byte laneIndex, String toGeo) {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.convert3D(edgeID, pos, laneIndex, toGeo)));
    }

    public SumoStringList Simulation_convertGeo(double x, double y, boolean fromGeo) {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.convertGeo(x, y, fromGeo)));
    }

    public SumoPosition2D Simulation_convertRoad(double x, double y, String isGeo) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Simulation.convertRoad(x, y, isGeo)));
    }

    public SumoStringList Simulation_getArrivedIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getArrivedIDList()));
    }

    public int Simulation_getArrivedNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getArrivedNumber()));
    }

    public int Simulation_getCurrentTime() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getCurrentTime()));
    }

    public int Simulation_getBusStopWaiting() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getBusStopWaiting()));
    }

    public SumoStringList Simulation_getParkingEndingVehiclesIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getParkingEndingVehiclesIDList()));
    }

    public int Simulation_getParkingEndingVehiclesNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getParkingEndingVehiclesNumber()));
    }

    public SumoStringList Simulation_getParkingStartingVehiclesIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getParkingStartingVehiclesIDList()));
    }

    public int Simulation_getParkingStartingVehiclesNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getParkingStartingVehiclesNumber()));
    }

    public SumoStringList Simulation_getStopEndingVehiclesIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getStopEndingVehiclesIDList()));
    }

    public int Simulation_getStopEndingVehiclesNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getStopEndingVehiclesNumber()));
    }

    public SumoStringList Simulation_getStopStartingVehiclesIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getStopStartingVehiclesIDList()));
    }

    public int Simulation_getStopStartingVehiclesNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getStopStartingVehiclesNumber()));
    }

    public int Simulation_getDeltaT() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getDeltaT()));
    }

    public SumoStringList Simulation_getDepartedIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getDepartedIDList()));
    }

    public int Simulation_getDepartedNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getDepartedNumber()));
    }

    public double Simulation_getDistance2D(double x1, double y1, double x2, double y2, boolean isGeo, boolean isDriving) {
        return this.helper.getDouble(this.sumo.get_cmd(Simulation.getDistance2D(x1, y1, x2, y2, isGeo, isDriving)));
    }

    public double Simulation_getDistanceRoad(String edgeID1, double pos1, String edgeID2, double pos2, boolean isDriving) {
        return this.helper.getDouble(this.sumo.get_cmd(Simulation.getDistanceRoad(edgeID1, pos1, edgeID2, pos2, isDriving)));
    }

    public SumoStringList Simulation_getEndingTeleportIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getEndingTeleportIDList()));
    }

    public int Simulation_getEndingTeleportNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getEndingTeleportNumber()));
    }

    public SumoStringList Simulation_getLoadedIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getLoadedIDList()));
    }

    public int Simulation_getLoadedNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getLoadedNumber()));
    }

    public int Simulation_getMinExpectedNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getMinExpectedNumber()));
    }

    public SumoStringList Simulation_getNetBoundary() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getNetBoundary()));
    }

    public SumoStringList Simulation_getStartingTeleportIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Simulation.getStartingTeleportIDList()));
    }

    public int Simulation_getStartingTeleportNumber() {
        return this.helper.getInt(this.sumo.get_cmd(Simulation.getStartingTeleportNumber()));
    }

    public SumoTLSController Trafficlights_getCompleteRedYellowGreenDefinition(String tlsID) {
        return this.helper.getTLSProgram(this.sumo.get_cmd(Trafficlight.getCompleteRedYellowGreenDefinition(tlsID)));
    }

    public SumoStringList Trafficlights_getControlledLanes(String tlsID) {
        return this.helper.getStringList(this.sumo.get_cmd(Trafficlight.getControlledLanes(tlsID)));
    }

    public SumoStringList Trafficlights_getControlledLinks(String tlsID) {
        return this.helper.getStringList(this.sumo.get_cmd(Trafficlight.getControlledLinks(tlsID)));
    }

    public SumoStringList Trafficlights_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Trafficlight.getIDList()));
    }

    public int Trafficlights_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Trafficlight.getIDCount()));
    }

    public int Trafficlights_getNextSwitch(String tlsID) {
        return this.helper.getInt(this.sumo.get_cmd(Trafficlight.getNextSwitch(tlsID)));
    }

    public int Trafficlights_getPhaseDuration(String tlsID) {
        return this.helper.getInt(this.sumo.get_cmd(Trafficlight.getPhaseDuration(tlsID)));
    }

    public int Trafficlights_getPhase(String tlsID) {
        return this.helper.getInt(this.sumo.get_cmd(Trafficlight.getPhase(tlsID)));
    }

    public String Trafficlights_getProgram(String tlsID) {
        return this.helper.getString(this.sumo.get_cmd(Trafficlight.getProgram(tlsID)));
    }

    public String Trafficlights_getRedYellowGreenState(String tlsID) {
        return this.helper.getString(this.sumo.get_cmd(Trafficlight.getRedYellowGreenState(tlsID)));
    }

    public double Vehicletype_getAccel(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getAccel(typeID)));
    }

    public SumoColor Vehicletype_getColor(String typeID) {
        return this.helper.getColor(this.sumo.get_cmd(Vehicletype.getColor(typeID)));
    }

    public double Vehicletype_getDecel(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getDecel(typeID)));
    }

    public String Vehicletype_getEmissionClass(String typeID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicletype.getEmissionClass(typeID)));
    }

    public SumoStringList Vehicletype_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Vehicletype.getIDList()));
    }

    public int Vehicletype_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Vehicletype.getIDCount()));
    }

    public double Vehicletype_getImperfection(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getImperfection(typeID)));
    }

    public double Vehicletype_getLength(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getLength(typeID)));
    }

    public double Vehicletype_getMaxSpeed(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getMaxSpeed(typeID)));
    }

    public double Vehicletype_getMinGap(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getMinGap(typeID)));
    }

    public String Vehicletype_getShapeClass(String typeID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicletype.getShapeClass(typeID)));
    }

    public double Vehicletype_getSpeedDeviation(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getSpeedDeviation(typeID)));
    }

    public double Vehicletype_getSpeedFactor(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getSpeedFactor(typeID)));
    }

    public double Vehicletype_getTau(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getTau(typeID)));
    }

    public String Vehicletype_getVehicleClass(String typeID) {
        return this.helper.getString(this.sumo.get_cmd(Vehicletype.getVehicleClass(typeID)));
    }

    public double Vehicletype_getWidth(String typeID) {
        return this.helper.getDouble(this.sumo.get_cmd(Vehicletype.getWidth(typeID)));
    }

    public SumoStringList Lane_getAllowed(String laneID) {
        return this.helper.getStringList(this.sumo.get_cmd(Lane.getAllowed(laneID)));
    }

    public double Lane_getCO2Emission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getCO2Emission(laneID)));
    }

    public double Lane_getCOEmission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getCOEmission(laneID)));
    }

    public SumoStringList Lane_getDisallowed(String laneID) {
        return this.helper.getStringList(this.sumo.get_cmd(Lane.getDisallowed(laneID)));
    }

    public String Lane_getEdgeID(String laneID) {
        return this.helper.getString(this.sumo.get_cmd(Lane.getEdgeID(laneID)));
    }

    public String Lane_getElectricityConsumption(String laneID) {
        return this.helper.getString(this.sumo.get_cmd(Lane.getElectricityConsumption(laneID)));
    }

    public double Lane_getFuelConsumption(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getFuelConsumption(laneID)));
    }

    public double Lane_getHCEmission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getHCEmission(laneID)));
    }

    public SumoStringList Lane_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Lane.getIDList()));
    }

    public int Lane_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Lane.getIDCount()));
    }

    public int Lane_getLastStepHaltingNumber(String laneID) {
        return this.helper.getInt(this.sumo.get_cmd(Lane.getLastStepHaltingNumber(laneID)));
    }

    public double Lane_getLastStepLength(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getLastStepLength(laneID)));
    }

    public double Lane_getLastStepMeanSpeed(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getLastStepMeanSpeed(laneID)));
    }

    public double Lane_getLastStepOccupancy(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getLastStepOccupancy(laneID)));
    }

    public SumoStringList Lane_getLastStepVehicleIDs(String laneID) {
        return this.helper.getStringList(this.sumo.get_cmd(Lane.getLastStepVehicleIDs(laneID)));
    }

    public int Lane_getLastStepVehicleNumber(String laneID) {
        return this.helper.getInt(this.sumo.get_cmd(Lane.getLastStepVehicleNumber(laneID)));
    }

    public double Lane_getLength(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getLength(laneID)));
    }

    public byte Lane_getLinkNumber(String laneID) {
        return this.helper.getByte(this.sumo.get_cmd(Lane.getLinkNumber(laneID)));
    }

    public SumoLinkList Lane_getLinks(String laneID) {
        return this.helper.getLaneLinks(this.sumo.get_cmd(Lane.getLinks(laneID)));
    }

    public double Lane_getMaxSpeed(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getMaxSpeed(laneID)));
    }

    public double Lane_getWaitingTime(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getWaitingTime(laneID)));
    }

    public double Lane_getNOxEmission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getNOxEmission(laneID)));
    }

    public double Lane_getNoiseEmission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getNoiseEmission(laneID)));
    }

    public double Lane_getPMxEmission(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getPMxEmission(laneID)));
    }

    public SumoGeometry Lane_getShape(String laneID) {
        return this.helper.getPolygon(this.sumo.get_cmd(Lane.getShape(laneID)));
    }

    public double Lane_getTraveltime(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getTraveltime(laneID)));
    }

    public double Lane_getWidth(String laneID) {
        return this.helper.getDouble(this.sumo.get_cmd(Lane.getWidth(laneID)));
    }

    public SumoColor Polygon_getColor(String polygonID) {
        return this.helper.getColor(this.sumo.get_cmd(Polygon.getColor(polygonID)));
    }

    public SumoStringList Polygon_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Polygon.getIDList()));
    }

    public int Polygon_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Polygon.getIDCount()));
    }

    public SumoGeometry Polygon_getShape(String polygonID) {
        return this.helper.getPolygon(this.sumo.get_cmd(Polygon.getShape(polygonID)));
    }

    public String Polygon_getType(String polygonID) {
        return this.helper.getString(this.sumo.get_cmd(Polygon.getType(polygonID)));
    }

    public void Poi_add(String poiID, double x, double y, SumoColor color, String poiType, int layer) {
        this.sumo.get_cmd(Poi.add(poiID, x, y, color, poiType, layer));
    }

    public SumoColor Poi_getColor(String poiID) {
        return this.helper.getColor(this.sumo.get_cmd(Poi.getColor(poiID)));
    }

    public SumoStringList Poi_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Poi.getIDList()));
    }

    public int Poi_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Poi.getIDCount()));
    }

    public SumoPosition2D Poi_getPosition(String poiID) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Poi.getPosition(poiID)));
    }

    public String Poi_getType(String poiID) {
        return this.helper.getString(this.sumo.get_cmd(Poi.getType(poiID)));
    }

    public SumoStringList Junction_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Junction.getIDList()));
    }

    public SumoGeometry Junction_getShape(String junctionID) {
        return this.helper.getPolygon(this.sumo.get_cmd(Junction.getShape(junctionID)));
    }

    public int Junction_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Junction.getIDCount()));
    }

    public SumoPosition2D Junction_getPosition(String junctionID) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Junction.getPosition(junctionID)));
    }

    public SumoBoundingBox GUI_getBoundary(String viewID) {
        return this.helper.getBoundingBox(this.sumo.get_cmd(Gui.getBoundary(viewID)));
    }

    public SumoStringList GUI_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Gui.getIDList()));
    }

    public SumoPosition2D GUI_getOffset(String viewID) {
        return this.helper.getPosition2D(this.sumo.get_cmd(Gui.getOffset(viewID)));
    }

    public String GUI_getSchema(String viewID) {
        return this.helper.getString(this.sumo.get_cmd(Gui.getSchema(viewID)));
    }

    public double GUI_getZoom(String viewID) {
        return this.helper.getDouble(this.sumo.get_cmd(Gui.getZoom(viewID)));
    }

    public SumoStringList Route_getEdges(String routeID) {
        return this.helper.getStringList(this.sumo.get_cmd(Route.getEdges(routeID)));
    }

    public SumoStringList Route_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Route.getIDList()));
    }

    public int Route_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Route.getIDCount()));
    }

    public SumoStringList Inductionloop_getIDList() {
        return this.helper.getStringList(this.sumo.get_cmd(Inductionloop.getIDList()));
    }

    public int Inductionloop_getIDCount() {
        return this.helper.getInt(this.sumo.get_cmd(Inductionloop.getIDCount()));
    }

    public String Inductionloop_getLaneID(String loopID) {
        return this.helper.getString(this.sumo.get_cmd(Inductionloop.getLaneID(loopID)));
    }

    public double Inductionloop_getLastStepMeanLength(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Inductionloop.getLastStepMeanLength(loopID)));
    }

    public double Inductionloop_getLastStepMeanSpeed(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Inductionloop.getLastStepMeanSpeed(loopID)));
    }

    public double Inductionloop_getLastStepOccupancy(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Inductionloop.getLastStepOccupancy(loopID)));
    }

    public SumoStringList Inductionloop_getLastStepVehicleIDs(String loopID) {
        return this.helper.getStringList(this.sumo.get_cmd(Inductionloop.getLastStepVehicleIDs(loopID)));
    }

    public int Inductionloop_getLastStepVehicleNumber(String loopID) {
        return this.helper.getInt(this.sumo.get_cmd(Inductionloop.getLastStepVehicleNumber(loopID)));
    }

    public double Inductionloop_getPosition(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Inductionloop.getPosition(loopID)));
    }

    public double Inductionloop_getTimeSinceDetection(String loopID) {
        return this.helper.getDouble(this.sumo.get_cmd(Inductionloop.getTimeSinceDetection(loopID)));
    }

    public SumoVehicleData Inductionloop_getVehicleData(String loopID) {
        return this.helper.getVehicleData(this.sumo.get_cmd(Inductionloop.getVehicleData(loopID)));
    }

}
