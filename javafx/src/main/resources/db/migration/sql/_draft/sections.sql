CREATE TABLE sections (
    section_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    CONSTRAINT uq_section_work UNIQUE (section_id, work_id),
    CONSTRAINT fk_section_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE section_hierarchy_map (
    section_id BIGINT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    work_id BIGINT NOT NULL,
    CONSTRAINT fk_section_hierarchy_section FOREIGN KEY (section_id, work_id) REFERENCES sections(section_id, work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_section_hierarchy_parent FOREIGN KEY (parent_id, work_id) REFERENCES sections(section_id, work_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE episode_section_map (
    episode_id BIGINT PRIMARY KEY,
    section_id BIGINT NOT NULL,
    work_id BIGINT NOT NULL,
    CONSTRAINT fk_episode_section_episode FOREIGN KEY (episode_id, work_id) REFERENCES episodes(episode_id, work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_episode_section_section FOREIGN KEY (section_id, work_id) REFERENCES sections(section_id, work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);
