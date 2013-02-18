package org.gsgj.mybus;

public class BusInfo {
	private String busStopName;
	private String busNumber;
	private String busDestination;
	private String beforeBusStopCount;
	private String estimatedTime;
	
	public String getBusStopName() {
		return busStopName;
	}
	public void setBusStopName(String busStopName) {
		this.busStopName = busStopName;
	}
	public String getBusNumber() {
		return busNumber;
	}
	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}
	public String getBusDestination() {
		return busDestination;
	}
	public void setBusDestination(String busDestination) {
		this.busDestination = busDestination;
	}
	public String getBeforeBusStopCount() {
		return beforeBusStopCount;
	}
	public void setBeforeBusStopCount(String beforeBusStopCount) {
		this.beforeBusStopCount = beforeBusStopCount;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
	
}
