CREATE TABLE sites (
    site_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(256) NOT NULL
);

CREATE TABLE hostnames (
    host_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hostname VARCHAR(2048) UNIQUE NOT NULL,
    site_id BIGINT NOT NULL,
    description VARCHAR(256) NOT NULL,
    CONSTRAINT fk_hostname_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE universes (
    universe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL
);

CREATE TABLE works (
    work_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_id BIGINT NOT NULL,
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

CREATE TABLE work_title_updates (
    title_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    updated_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_work_update UNIQUE (work_id, updated_at),
    CONSTRAINT fk_title_update_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
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
    posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_episode_post UNIQUE (episode_id, posted_at),
    CONSTRAINT fk_post_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_deletes (
    episode_id BIGINT PRIMARY KEY,
    deleted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_delete_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE contributors (
    contributor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
);

CREATE TABLE contributors_sites_map (
    contributor_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    handle_name VARCHAR(256) NOT NULL,
    in_site_id VARCHAR(256) NOT NULL,
    CONSTRAINT pk_contributor_site PRIMARY KEY (contributor_id, site_id),
    CONSTRAINT uq_site_in_site UNIQUE (site_id, in_site_id),
    CONSTRAINT fk_contributors_sites_contributor FOREIGN KEY contributor_id REFERENCES contributors(contributor_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_contributors_sites_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE contribution_types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE contributions (
    contribution_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    contributor_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT fk_contribution_work_site FOREIGN KEY (work_id, site_id) REFERENCES works(work_id, site_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_contribution_contributor_site FOREIGN KEY (contributor_id, site_id) REFERENCES contributors_sites(contributor_id, site_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_contribution_contribution_type FOREIGN KEY type_id REFERENCES contribution_types(type_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
