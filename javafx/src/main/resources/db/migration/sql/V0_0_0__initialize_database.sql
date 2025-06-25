
CREATE TABLE universes (
    universe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL
);

CREATE TABLE works (
    work_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_id INT NOT NULL,
    summary CLOB NOT NULL,
    registered_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE works_universe_map (
    work_id BIGINT PRIMARY KEY,
    universe_id BIGINT NOT NULL,
    CONSTRAINT fk_works_universe_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_universe_universe FOREIGN KEY (universe_id) REFERENCES universes(universe_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE work_title_updates (
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    updated_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_work_title_update PRIMARY KEY (work_id, updated_at),
    CONSTRAINT fk_title_update_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE narou_works (
    work_id BIGINT PRIMARY KEY,
    ncode VARCHAR(12) UNIQUE NOT NULL,
    narou_id BIGINT UNIQUE NOT NULL,
    work_type INT NOT NULL,
    genre_id INT NOT NULL,
    first_posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_narou_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episodes (
    episode_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    order_number BIGINT NOT NULL,
    first_posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_episode_order UNIQUE (work_id, order_number),
    CONSTRAINT fk_episode_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    episode_id BIGINT NOT NULL,
    posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_episode_post UNIQUE (episode_id, posted_at),
    CONSTRAINT fk_post_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_deletes (
    episode_id BIGINT PRIMARY KEY,
    deleted_before TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_delete_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE contributors (
    contributor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    handle_name VARCHAR(256) NOT NULL
);

CREATE TABLE contribution_types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(256) UNIQUE NOT NULL
);
INSERT INTO contribution_types (type_name) VALUES ('作者'), ('イラスト'), ('原作'), ('原案');

CREATE TABLE contributions (
    work_id BIGINT NOT NULL,
    contributor_id BIGINT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT pk_contribution PRIMARY KEY (work_id, contributor_id, type_id),
    CONSTRAINT fk_contribution_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_contribution_contribution_type FOREIGN KEY (type_id) REFERENCES contribution_types(type_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE hyperlinks (
    link_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    link VARCHAR(2048) UNIQUE NOT NULL,
    description VARCHAR(256) NOT NULL
);

CREATE TABLE hyperlink_work_map (
    link_id BIGINT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    CONSTRAINT fk_hyperlink_work_link FOREIGN KEY (link_id) REFERENCES hyperlinks(link_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_hyperlink_work_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE hyperlink_contributor_map (
    link_id BIGINT PRIMARY KEY,
    contributor_id BIGINT NOT NULL,
    CONSTRAINT fk_hyperlink_contributor_link FOREIGN KEY (link_id) REFERENCES hyperlinks(link_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_hyperlink_contributor_contributor FOREIGN KEY (contributor_id) REFERENCES contributors(contributor_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
