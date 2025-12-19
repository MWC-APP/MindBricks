package ch.inf.usi.mindbricks.ui.nav;

/**
 * This interface is implemented by views that may require to temporary disable the navigation bar.
 *
 * @author Luca Di Bello
 * @author Loca0307
 */
public interface NavigationLocker {
    void setNavigationEnabled(boolean enabled);
}
