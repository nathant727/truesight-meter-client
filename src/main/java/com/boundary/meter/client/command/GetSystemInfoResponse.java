package com.boundary.meter.client.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.immutables.value.Value;

import java.util.Iterator;
import java.util.Optional;

/**
 * Read and parse response from System Information JSON RPC call
 */

@Value.Immutable
public abstract class GetSystemInfoResponse implements Response {

    public abstract String meterVersion();
    public abstract String hostname();
    public abstract Optional<String> mach();
    public abstract Optional<String> osver();
    public abstract Optional<String> machdesc();
    public abstract Optional<String> osname();
    public abstract Optional<String> arch();
    public abstract Optional<String> version();
    public abstract Optional<String> vendname();
    public abstract Optional<String> patch();
    public abstract Optional<JsonNode> cpus();
    public abstract Optional<JsonNode> filesystems();
    public abstract Optional<JsonNode> memory();
    public abstract Optional<JsonNode> interfaces();
    public abstract Optional<JsonNode> packages();
    public abstract Optional<JsonNode> listeners();

    public static GetSystemInfoResponse of(int id, JsonNode resp) {
        ImmutableGetSystemInfoResponse.Builder infoBuilder = ImmutableGetSystemInfoResponse.builder();
        JsonNode result = resp.get("result");

        infoBuilder.id(id);
        if (result.has("system_info")) {
            JsonNode system_info = result.get("system_info");
            if (system_info.has("meter_version")) {
                infoBuilder.meterVersion(result.get("meter_version").asText());
            }
            if (system_info.has("hostname")) {
                infoBuilder.hostname(result.get("hostname").asText());
            }
            if (system_info.has("mach")) {
                infoBuilder.mach(result.get("mach").asText());
            }
            if (system_info.has("osver")) {
                infoBuilder.osver(result.get("osver").asText());
            }
            if (system_info.has("machdesc")) {
                infoBuilder.machdesc(result.get("machdesc").asText());
            }
            if (system_info.has("osname")) {
                infoBuilder.osname(result.get("osname").asText());
            }
            if (system_info.has("arch")) {
                infoBuilder.arch(result.get("arch").asText());
            }
            if (system_info.has("version")) {
                infoBuilder.version(result.get("version").asText());
            }
            if (system_info.has("vendname")) {
                infoBuilder.vendname(result.get("vendname").asText());
            }
            if (system_info.has("patch")) {
                infoBuilder.patch(result.get("patch").asText());
            }
            if (system_info.has("cpus")) {
                infoBuilder.cpus(result.get("cpus"));
            }
            if (system_info.has("filesystems")) {
                infoBuilder.filesystems(result.get("filesystems"));
            }
            if (system_info.has("memory")) {
                infoBuilder.memory(result.get("memory"));
            }
            if (system_info.has("interfaces")) {
                infoBuilder.interfaces(result.get("interfaces"));
            }
            if (system_info.has("discovered_packages")) {
                infoBuilder.packages(result.get("discovered_packages"));
            }
            if (system_info.has("app_listeners")) {
                infoBuilder.listeners(result.get("app_listeners"));
            }
        }
        return infoBuilder.build();
    }

    @Override
    public String toString() {
        String returnVal = "GetSystemInfoResponse{" +
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

        if (cpus().get().isArray()) {
            Iterator<JsonNode> ite = cpus().get().elements();
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
        if (filesystems().get().isArray()) {
            Iterator<JsonNode> ite = filesystems().get().elements();
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
        if (!memory().get().isNull()) {

            returnVal = ", Memory Installed='" + memory().get().get("installed") + '\'' +
                    ", Usable Memory='" + memory().get().get("usable") + '\'';
        }
        if (interfaces().get().isArray()) {
            Iterator<JsonNode> ite = interfaces().get().elements();
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
        if (packages().get().isArray()) {
            Iterator<JsonNode> ite = packages().get().elements();
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
        if (listeners().get().isArray()) {
            Iterator<JsonNode> ite = listeners().get().elements();
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

}
