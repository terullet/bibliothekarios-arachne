CREATE TABLE reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_type INT NOT NULL,
    registered_at TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
    review_id BIGINT PRIMARY KEY,
    comment CLOB NOT NULL,
    CONSTRAINT fk_comment_review FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE ratings (
    review_id BIGINT PRIMARY KEY,
    rate FLOAT NOT NULL,
    CONSTRAINT fk_rating_review FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE review_work_map (
    review_id BIGINT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    CONSTRAINT fk_reviews_works_review FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reviews_works_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE review_episodes_map (
    review_id BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    CONSTRAINT pk_reviews_episodes PRIMARY KEY (review_id, episode_id),
    CONSTRAINT fk_reviews_episodes_review FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reviews_episodes_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE review_contributor_map (
    review_id BIGINT PRIMARY KEY,
    contributor_id BIGINT NOT NULL,
    CONSTRAINT fk_review_contributor_review FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_review_contributor_contributor FOREIGN KEY (contributor_id) REFERENCES contributors(contributor_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
