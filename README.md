# SkyBlock Flipper

`SkyBlock Flipper` is the flip-analysis service template. It consumes auction events from `SkyBlock Updater`, evaluates them, and publishes flip events for other consumers.

## Important: private implementation

This repository does not contain the actual flip-analysis algorithm. `skyblockflipper/service/FlipperEngineService` is intentionally left as a template and currently returns no estimated profit. Your implementation must be added locally before this service can produce meaningful flips.

The surrounding Kafka consumer, publisher, persistence, and shared model integration are included so that the private engine can be connected without changing the service contract.

## Events

The service consumes:

-   `updater-newauction` - An `AuctionActive` object serialized as JSON.
-   `updater-endedauction` - The UUID of an ended auction.

It publishes:

-   `flipper-newflip` - A `Flip` object serialized as JSON.
-   `flipper-endedflip` - The UUID of the auction associated with the ended flip.

The topic names and message handling are implemented in `KafkaConsumerService` and `KafkaPublisherService`.

## Requirements

-   Java 21
-   Maven
-   PostgreSQL and a Kafka-compatible broker for a complete runtime
-   Your `FlipperEngineService` implementation, if useful flip results are required

## Build locally

Install the shared models artifact first, then build this service:

```bash
# Run in skyblock-shared-models
mvn clean install

# Run in skyblock-flipper
mvn clean verify
```

The template is expected to compile with the placeholder engine, but it does not provide a usable flip strategy until the private implementation is supplied.

## Run with Docker Compose

From the backend stack's root directory:

```bash
docker compose up --build flipper
```

The container connects to PostgreSQL at `postgres_db:5432` and Kafka at `redpanda:9092`. In normal operation, run it together with the updater so that auction events are available:

```bash
docker compose up --build updater flipper
```

View its logs with:

```bash
docker logs -f skyblock-flipper
```

## License

This project is licensed under the **GNU General Public License v3.0 only (GPL-3.0-only)**. See [LICENSE.txt](LICENSE.txt) for the full license text.

## Disclaimer

This project is not affiliated with, endorsed by, or associated with Hypixel Inc. "Hypixel" and related names are trademarks of Hypixel Inc. This is an independent community project intended for educational and personal use.
