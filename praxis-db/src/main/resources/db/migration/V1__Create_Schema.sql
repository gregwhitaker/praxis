--
-- Copyright 2019 Greg Whitaker
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--    http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE UNLOGGED TABLE event_ledger (
    led_id          UUID        PRIMARY KEY,
    led_ts          TIMESTAMP   NOT NULL,
    led_proc_ts     TIMESTAMP,
    evt_data        BYTEA       NOT NULL
)
-- PARTITION BY RANGE (led_ts)
WITH (autovacuum_enabled=false);

CREATE TABLE events (
    evt_id          UUID            PRIMARY KEY,
    evt_corr_id     UUID,
    evt_type        BIGINT          NOT NULL,
    evt_ts          TIMESTAMP       NOT NULL,
    evt_proc_ts     TIMESTAMP       NOT NULL,
    evt_app         VARCHAR(250),
    evt_ins         VARCHAR(250),
    evt_env         VARCHAR(250),
    evt_attrs       JSONB
);
CREATE INDEX events_corr_id ON events(evt_corr_id);