package net.browsertime.tool.webdriver;

import com.google.inject.Provider;
import net.browsertime.tool.BrowserConfig;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

abstract public class WebDriverProvider implements Provider<WebDriver> {
  final Map<BrowserConfig, String> browserConfiguration;

  WebDriverProvider(Map<BrowserConfig, String> browserConfiguration) {
    this.browserConfiguration = browserConfiguration;
  }

  protected DesiredCapabilities createCapabilities() {
    DesiredCapabilities c = getBrowserCapabilities();

    setProxyCapability(c);

    return c;
  }

  protected abstract DesiredCapabilities getBrowserCapabilities();

  /**
   * Validate that the provider can run, e.g. that external dependencies as fulfilled.
   * 
   * @throws WebDriverValidationException if provider isn't able to run.
   */
  public abstract void validateProvider() throws WebDriverValidationException;

  private void setProxyCapability(DesiredCapabilities c) {
    String proxyHost = browserConfiguration.get(BrowserConfig.proxyHost);
    if (proxyHost != null) {
      Proxy proxy = new Proxy();
      proxy.setHttpProxy(proxyHost);
      c.setCapability(CapabilityType.PROXY, proxy);
    }
  }
}