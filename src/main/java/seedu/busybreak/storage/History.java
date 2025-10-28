package seedu.busybreak.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public final class History {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path HISTORY_ROOT = DATA_DIR.resolve("history");
    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
    private static final String SNAP_PREFIX = "snapshot_";

    private static final String A = "activities.txt";
    private static final String B = "budgets.txt";
    private static final String T = "trips.txt";
    private static final int MAX_SNAPSHOTS = 50;


    public static void checkpointWithSave(Storage storage) {
        try {
            Files.createDirectories(DATA_DIR);

            storage.saveActivities();
            storage.saveBudgets();
            storage.saveTrips();

            Files.createDirectories(HISTORY_ROOT);
            Path snapDir = HISTORY_ROOT.resolve(SNAP_PREFIX + LocalDateTime.now().format(TS));
            Files.createDirectories(snapDir);

            copyIfExists(DATA_DIR.resolve(A), snapDir.resolve(A));
            copyIfExists(DATA_DIR.resolve(B), snapDir.resolve(B));
            copyIfExists(DATA_DIR.resolve(T), snapDir.resolve(T));

            trimOldSnapshots();
        } catch (IOException e) {
            System.err.println("[History] Snapshot failed: " + e.getMessage());
        }
    }

    public static boolean hasSnapshots() {
        if (!Files.exists(HISTORY_ROOT)) {
            return false;
        }
        try (Stream<Path> s = Files.list(HISTORY_ROOT)) {
            return s.anyMatch(p -> Files.isDirectory(p)
                    && p.getFileName().toString().startsWith(SNAP_PREFIX));
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<Path> restoreLatest() {
        try {
            Optional<Path> latest = latestSnapshotDir();
            if (latest.isEmpty()) {
                return Optional.empty();
            }

            Path d = latest.get();
            copyIfExists(d.resolve(A), DATA_DIR.resolve(A));
            copyIfExists(d.resolve(B), DATA_DIR.resolve(B));
            copyIfExists(d.resolve(T), DATA_DIR.resolve(T));

            deleteDirectory(d); // consume one level of undo
            return latest;
        } catch (IOException e) {
            System.err.println("[History] Restore failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    // ---- Helpers ----
    private static void copyIfExists(Path src, Path dst) throws IOException {
        if (Files.exists(src)) {
            Files.createDirectories(dst.getParent());
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.deleteIfExists(dst);
        }
    }

    private static Optional<Path> latestSnapshotDir() throws IOException {
        if (!Files.exists(HISTORY_ROOT)) {
            return Optional.empty();
        }
        try (Stream<Path> s = Files.list(HISTORY_ROOT)) {
            return s.filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().startsWith(SNAP_PREFIX))
                    .max(Comparator.comparing(Path::getFileName));
        }
    }

    private static void trimOldSnapshots() throws IOException {
        if (!Files.exists(HISTORY_ROOT)) {
            return;
        }
        try (Stream<Path> s = Files.list(HISTORY_ROOT)) {
            var snaps = s.filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().startsWith(SNAP_PREFIX))
                    .sorted(Comparator.comparing(Path::getFileName).reversed())
                    .toList();
            for (int i = MAX_SNAPSHOTS; i < snaps.size(); i++) {
                deleteDirectory(snaps.get(i));
            }
        }
    }

    private static void deleteDirectory(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        try (Stream<Path> s = Files.walk(dir)) {
            s.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.deleteIfExists(p);
                } catch (IOException e) {
                    System.err.println("[History] Could not delete " + p + ": " + e.getMessage());
                }
            });
        }
    }
}
