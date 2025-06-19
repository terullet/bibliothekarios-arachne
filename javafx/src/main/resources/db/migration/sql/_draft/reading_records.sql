CREATE TABLE reading_records (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    read_seconds INT NOT NULL,
    read_from TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    read_to TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE reading_record_paragraphs_map (
    record_id BIGINT NOT NULL,
    paragraph_id BIGINT NOT NULL,
    CONSTRAINT pk_reading_record_paragraph PRIMARY KEY (record_id, paragraph_id),
    CONSTRAINT fk_reading_record_paragraphs_record FOREIGN KEY (record_id) REFERENCES reading_records(record_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reading_record_paragraphs_paragraph FOREIGN KEY (paragraph_id) REFERENCES paragraphs(paragraph_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE reading_record_episode_map (
    record_id BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    CONSTRAINT pk_reading_record_episode PRIMARY KEY (record_id, episode_id),
    CONSTRAINT fk_reading_record_episode_record FOREIGN KEY (record_id) REFERENCES reading_records(record_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reading_record_episode_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
