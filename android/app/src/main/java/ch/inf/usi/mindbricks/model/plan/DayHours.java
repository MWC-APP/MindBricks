package ch.inf.usi.mindbricks.model.plan;

/**
 * Record representing the number of hours allocated to a specific day.
 *
 * @author Luca Di Bello
 */
public record DayHours(DayKey dayKey, float hours) {
}
