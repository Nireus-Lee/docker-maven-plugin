package io.fabric8.maven.docker.config;

import java.util.List;

import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author roland
 * @since 12.10.14
 */
public class WaitConfiguration {

    @Parameter
    private int time;

    /**
     * @deprecated Use &lt;http&gt;&lturl&gt;&lt;/url&gt;&lt;/http&gt; instead
     */
    @Parameter
    private String url;

    @Parameter
    private HttpConfiguration http;

    @Parameter
    private ExecConfiguration exec;

    @Parameter
    private TcpConfiguration tcp;

    @Parameter
    private String log;

    @Parameter
    private int shutdown;

    @Parameter
    private int kill;

    public WaitConfiguration() {}

    private WaitConfiguration(int time, ExecConfiguration exec, HttpConfiguration http, TcpConfiguration tcp, String log, int shutdown, int kill) {
        this.time = time;
        this.exec = exec;
        this.http = http;
        this.tcp = tcp;
        this.log = log;
        this.shutdown = shutdown;
        this.kill = kill;
    }

    public int getTime() {
        return time;
    }

    public String getUrl() {
        return http != null ? http.getUrl() : url;
    }

    public ExecConfiguration getExec() {
        return exec;
    }

    public HttpConfiguration getHttp() {
        return http;
    }

    public TcpConfiguration getTcp() {
        return tcp;
    }

    public String getLog() {
        return log;
    }

    public int getShutdown() {
        return shutdown;
    }

    public int getKill() {
        return kill;
    }

    // =============================================================================

    public static class Builder {
        private int time = 0,shutdown = 0, kill = 0;
        private String url,log,status;
        private String method;
        private String preStop;
        private String postStart;
        private List<Integer> tcpPorts;
        private String tcpHost;

        public Builder time(int time) {
            this.time = time;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder log(String log) {
            this.log = log;
            return this;
        }

        public Builder shutdown(int shutdown) {
            this.shutdown = shutdown;
            return this;
        }

        public Builder kill(int kill) {
            this.kill = kill;
            return this;
        }

        public Builder tcpPorts(List<Integer> tcpPorts)
        {
            this.tcpPorts = tcpPorts;
            return this;
        }

        public Builder tcpHost(String tcpHost)
        {
            this.tcpHost = tcpHost;
            return this;
        }

        public WaitConfiguration build() {
            return new WaitConfiguration(time,
                                         postStart != null || preStop != null ? new ExecConfiguration(postStart, preStop) : null,
                                         url != null ? new HttpConfiguration(url,method,status) : null,
                                         tcpPorts != null ? new TcpConfiguration(tcpHost, tcpPorts) : null,
                                         log,
                                         shutdown,
                                         kill);
        }

        public Builder preStop(String command) {
            this.preStop = command;
            return this;
        }

        public Builder postStart(String command) {
            this.postStart = command;
            return this;
        }
    }

    public static class ExecConfiguration {
        @Parameter
        private String postStart;

        @Parameter
        private String preStop;

        public ExecConfiguration() {}

        public ExecConfiguration(String postStart, String preStop) {
            this.postStart = postStart;
            this.preStop = preStop;
        }

        public String getPostStart() {
            return postStart;
        }

        public String getPreStop() {
            return preStop;
        }
    }

    public static class HttpConfiguration {

        @Parameter
        private String url;

        @Parameter
        private String method;

        @Parameter
        private String status;

        public HttpConfiguration() {}

        private HttpConfiguration(String url, String method, String status) {
            this.url = url;
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class TcpConfiguration
    {
        @Parameter
        private String host;

        @Parameter
        private List<Integer> ports;

        public TcpConfiguration() {};

        private TcpConfiguration(String host, List<Integer> ports) {
            this.host = host;
            this.ports = ports;
        }

        public String getHost()
        {
            return host;
        }

        public List<Integer> getPorts()
        {
            return ports;
        }
    }

}
