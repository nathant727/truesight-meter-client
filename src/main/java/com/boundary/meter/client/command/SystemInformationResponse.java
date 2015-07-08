package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Iterator;

/**
 * Read and parse response from System Information JSON RPC call
 */

@Value.Immutable
public abstract class SystemInformationResponse implements Response {

    public abstract String meterVersion();
    public abstract String hostname();
    public abstract String mach();
    public abstract String osver();
    public abstract String machdesc();
    public abstract String osname();
    public abstract String arch();
    public abstract String version();
    public abstract String vendname();
    public abstract String patch();
    public abstract int id();
    public abstract JsonNode cpus();
    public abstract JsonNode filesystems();
    public abstract JsonNode memory();
    public abstract JsonNode interfaces();
    public abstract JsonNode packages();
    public abstract JsonNode listeners();

    public static SystemInformationResponse factory(int id, JsonNode resp) {
        JsonNode result = resp.get("result");
        return ImmutableSystemInformationResponse.builder()
                .id(id)
                .meterVersion(result.get("meter_version").asText())
                .hostname(result.get("hostname").asText())
                .mach(result.get("mach").asText())
                .osver(result.get("osver").asText())
                .machdesc(result.get("machdesc").asText())
                .osname(result.get("osname").asText())
                .arch(result.get("arch").asText())
                .version(result.get("version").asText())
                .vendname(result.get("vendname").asText())
                .patch(result.get("patch").asText())
                .cpus(result.get("cpus"))
                .filesystems(result.get("filesystems"))
                .memory(result.get("memory"))
                .interfaces(result.get("interfaces"))
                .packages(result.get("discovered_packages"))
                .listeners(result.get("app_listeners"))
                .build();

    }

    @Override
    public String toString() {
        String returnVal = "SystemInformationResponse{" +
                "meterVersion='" + meterVersion() + '\'' +
                ", Host='" + hostname() + '\'' +
                ", Architecture='" + arch() + '\'' +
                ", OS Mach='" + mach() + '\'' +
                ", OS Mach Description='" + machdesc() + '\'' +
                ", OS Name='" + osname() + '\'' +
                ", OS Version='" + version() + '\'' +
                ", OS Version Description='" + osver() + '\'' +
                ", OS Vendor Name='" + vendname() + '\'' +
                ", OS Patch Level='" + patch() + '\'';

        if (cpus().isArray()) {
            Iterator<JsonNode> ite = cpus().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode cpu = ite.next();
                String cpuvend = cpu.get("vendor").asText();
                String cpumodel = cpu.get("model").asText();
                String cpumhz = cpu.get("mhz").asText();
                String cpucache = cpu.get("cacheSize").asText();
                String cpusockets = cpu.get("totalSockets").asText();
                String cpucores = cpu.get("totalCores").asText();
                String cpucorespersocket = cpu.get("coresPerSocket").asText();
                returnVal = returnVal +
                        ", CPU" + i.toString() + " Vendor='" + cpuvend + '\'' +
                        ", CPU" + i.toString() + " Model='" + cpumodel + '\'' +
                        ", CPU" + i.toString() + " MHz='" + cpumhz + '\'' +
                        ", CPU" + i.toString() + " Cache='" + cpucache + '\'' +
                        ", CPU" + i.toString() + " Sockets='" + cpusockets + '\'' +
                        ", CPU" + i.toString() + " Cores='" + cpucores + '\'' +
                        ", CPU" + i.toString() + " Cores/Socket='" + cpucorespersocket + '\'';
                i++;
            }
        } else {
            returnVal = returnVal + ", CPUs=" + cpus();
        }
        if (filesystems().isArray()) {
            Iterator<JsonNode> ite = filesystems().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode filesystem = ite.next();
                String dirName = filesystem.get("dirName").asText();
                String devName = filesystem.get("devName").asText();
                String typeName = filesystem.get("typeName").asText();
                String sysTypeName = filesystem.get("sysTypeName").asText();
                returnVal = returnVal +
                        ", Filesystem" + i.toString() + " Directory='" + dirName + '\'' +
                        ", Filesystem" + i.toString() + " Device='" + devName + '\'' +
                        ", Filesystem" + i.toString() + " Type='" + typeName + '\'' +
                        ", Filesystem" + i.toString() + " System Type='" + sysTypeName + '\'';
                i++;
            }
        } else {
            returnVal = returnVal + ", Filesystems=" + filesystems();
        }
        if (!memory().isNull()) {

            returnVal = ", Memory Installed='" + memory().get("installed") + '\'' +
                    ", Usable Memory='" + memory().get("usable") + '\'';
        }
        if (interfaces().isArray()) {
            Iterator<JsonNode> ite = interfaces().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode networkInterface = ite.next();
                String name = networkInterface.get("name").asText();
                String type = networkInterface.get("type").asText();
                String mtu = networkInterface.get("mtu").asText();
                String driverName = networkInterface.get("driver name").asText();
                String driverVersion = networkInterface.get("driver vers").asText();
                String firmwareVersion = networkInterface.get("firmware vers").asText();
                String ether = networkInterface.get("ether").asText();
                JsonNode addrs = networkInterface.get("addrs");
                String flagBits = networkInterface.get("flag_bits").asText();
                JsonNode flags = networkInterface.get("flags");
                returnVal = returnVal +
                        ", Interface" + i.toString() + " Name='" + name + '\'' +
                        ", Interface" + i.toString() + " Type='" + type + '\'' +
                        ", Interface" + i.toString() + " MTU='" + mtu + '\'' +
                        ", Interface" + i.toString() + " Driver Name='" + driverName + '\'' +
                        ", Interface" + i.toString() + " Driver Version='" + driverVersion + '\'' +
                        ", Interface" + i.toString() + " Firmware Version='" + firmwareVersion + '\'' +
                        ", Interface" + i.toString() + " Ethernet Address='" + ether + '\'';
                Iterator<JsonNode> adriter = addrs.elements();
                if (adriter.hasNext()) {
                    returnVal = returnVal + ", Interface" + i.toString() + " Addresses='";
                    while (adriter.hasNext()) {
                        returnVal = returnVal + adriter.next().asText();
                        if (adriter.hasNext()) {
                            returnVal = returnVal + ",";
                        }
                    }
                    returnVal = returnVal + '\'';
                }
                returnVal = returnVal + ", Interface" + i.toString() + " Flag Bits'" + flagBits + '\'';
                Iterator<JsonNode> flagiter = flags.elements();
                if (flagiter.hasNext()) {
                    returnVal = returnVal + ", Interface" + i.toString() + " Flags='";
                    while (flagiter.hasNext()) {
                        returnVal = returnVal + flagiter.next().asText();
                        if (flagiter.hasNext()) {
                            returnVal = returnVal + ",";
                        }
                    }
                    returnVal = returnVal + '\'';
                }
                i++;
            }
        } else {
            returnVal = returnVal + ", Interfaces='" + interfaces() + '\'';
        }
        if (packages().isArray()) {
            Iterator<JsonNode> ite = packages().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode discoveredPackage = ite.next();
                String name = discoveredPackage.get("name").asText();
                String version = discoveredPackage.get("version").asText();
                returnVal = returnVal +
                        ", Package" + i.toString() + " Name='" + name + '\'' +
                        ", Package" + i.toString() + " Version='" + version + '\'';
                i++;
            }
        } else {
            returnVal = returnVal + ", Discovered Packages='" + packages() + '\'';
        }
        if (listeners().isArray()) {
            Iterator<JsonNode> ite = listeners().elements();
            Integer i = 1;
            while (ite.hasNext()) {
                JsonNode listener = ite.next();
                String port = listener.get("port").asText();
                String protocol = listener.get("proto").asText();
                String process = listener.get("process").asText();
                returnVal = returnVal +
                        ", Application Listener" + i.toString() + " Port='" + port + '\'' +
                        ", Application Listener" + i.toString() + " Protocol='" + protocol + '\'' +
                        ", Application Listener" + i.toString() + " Version='" + process + '\'';
                i++;
            }
        } else {
            returnVal = returnVal + ", Application Listeners='" + listeners() + '\'';
        }
        returnVal = returnVal + ", id=" + id() + '}';
        return returnVal;
    }

    @Override
    public int getId() {
        return id();
    }
}
