CREATE TABLE universes (
    universe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL
);

CREATE TABLE works (
    work_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL,
    registered_at TIMESTAMP(3) WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT uq_work_site UNIQUE (work_id, site_id),
    CONSTRAINT fk_work_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE works_universe_map (
    work_id BIGINT PRIMARY KEY,
    universe_id BIGINT NOT NULL,
    CONSTRAINT fk_works_universe_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_universe_universe FOREIGN KEY universe_id REFERENCES universes(universe_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE narou_works (
    work_id BIGINT PRIMARY KEY,
    ncode VARCHAR(12) UNIQUE NOT NULL,
    narou_id BIGINT UNIQUE NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT fk_narou_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episodes (
    episode_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    order_number BIGINT NOT NULL,
    CONSTRAINT uq_episode_order UNIQUE (work_id, order_number),
    CONSTRAINT fk_episode_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    episode_id BIGINT NOT NULL,
    content CLOB NOT NULL,
    posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_episode_post UNIQUE (episode_id, posted_at),
    CONSTRAINT fk_post_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_deletes (
    episode_id BIGINT PRIMARY KEY,
    deleted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_delete_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);
