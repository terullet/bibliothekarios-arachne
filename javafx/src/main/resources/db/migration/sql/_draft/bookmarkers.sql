CREATE TABLE bookmarkers (
    bookmarker_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bookmarker_name VARCHAR(256) NOT NULL
);

CREATE TABLE bookmarker_paragraph_map (
    bookmarker_id BIGINT PRIMARY KEY,
    paragraph_id BIGINT NOT NULL,
    CONSTRAINT fk_bookmarker_paragraphs_bookmarker FOREIGN KEY (bookmarker_id) REFERENCES bookmarkers(bookmarker_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_bookmarker_paragraphs_paragraph FOREIGN KEY (paragraph_id) REFERENCES paragraphs(paragraph_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE bookmarker_episode_map (
    bookmarker_id BIGINT PRIMARY KEY,
    episode_id BIGINT NOT NULL,
    CONSTRAINT fk_bookmarker_episode_bookmarker FOREIGN KEY (bookmarker_id) REFERENCES bookmarkers(bookmarker_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_bookmarker_episode_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
