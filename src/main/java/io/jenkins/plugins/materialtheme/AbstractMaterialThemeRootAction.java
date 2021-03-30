package io.jenkins.plugins.materialtheme;

import hudson.Extension;
import hudson.model.UnprotectedRootAction;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.WebMethod;

import static java.util.Objects.requireNonNull;

abstract class AbstractMaterialThemeRootAction implements UnprotectedRootAction {
    public static final String LIGHT_THEME_CSS = "style/light-theme.css";
    public static final String MATERIAL_THEME_CSS = "material-theme.css";

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    public abstract String getThemeCss() throws IOException;

    public String getLightTheme() throws IOException {
        return readCssFile(this.LIGHT_THEME_CSS);
    }

    String readCssFile(String css_filename) throws IOException  {
        try (InputStream cssInputStream = getClass().getResourceAsStream(css_filename)) {
            requireNonNull(cssInputStream);
            return IOUtils.toString(cssInputStream, StandardCharsets.UTF_8);
        }
    }

    protected void doRedoThemeCss(StaplerRequest req, StaplerResponse res) throws IOException {
        res.setContentType("text/css");
        res.getWriter().print(this.getThemeCss());
        res.getWriter().print(this.getLightTheme());
    }
}
