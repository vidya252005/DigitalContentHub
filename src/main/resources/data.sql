-- ================================================
-- Digital Content Hub – Seed Data (no default users)
-- All users must register via /auth/register
-- ================================================

-- Content with plan tiers (no user seeds – register fresh)
MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (1, 'Introduction to Machine Learning',
        'A beginner-friendly guide to ML concepts and algorithms.',
        'EBOOK', 'Technology', 'Dr. A. Smith',
        '/content/files/ml-intro.pdf', '/content/thumbs/ml-intro.jpg',
        'FREE', 120, 4.5, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (2, 'Chill Beats Playlist Vol. 1',
        'Relaxing lo-fi music for study and work sessions.',
        'MUSIC', 'Lo-Fi', 'BeatMaker Studio',
        '/content/files/chill-beats.mp3', '/content/thumbs/chill-beats.jpg',
        'FREE', 350, 4.8, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (3, 'The Galaxy Chronicles',
        'An epic sci-fi movie about humanity''s first interstellar voyage.',
        'MOVIE', 'Sci-Fi', 'Cosmic Films',
        '/content/files/galaxy-chronicles.mp4', '/content/thumbs/galaxy.jpg',
        'MONTHLY', 980, 4.7, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (4, 'Advanced Spring Boot',
        'Deep dive into Spring Boot microservices, security, and JPA.',
        'EBOOK', 'Technology', 'Tech Authors Inc.',
        '/content/files/spring-boot.pdf', '/content/thumbs/spring.jpg',
        'MONTHLY', 210, 4.6, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (5, 'Jazz Classics Collection',
        'Timeless jazz recordings curated from the golden era of jazz.',
        'MUSIC', 'Jazz', 'Classic Records',
        '/content/files/jazz-classics.mp3', '/content/thumbs/jazz.jpg',
        'YEARLY', 450, 4.9, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (6, 'Cooking Masterclass',
        'Learn professional cooking techniques from world-class chefs.',
        'MOVIE', 'Lifestyle', 'Chef Academy',
        '/content/files/cooking-masterclass.mp4', '/content/thumbs/cooking.jpg',
        'FREE', 670, 4.4, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (7, 'The Art of Focus',
        'A guide to deep work, productivity, and eliminating distractions.',
        'EBOOK', 'Self-Help', 'Dr. R. Patel',
        '/content/files/art-of-focus.pdf', '/content/thumbs/focus.jpg',
        'MONTHLY', 180, 4.3, 1, CURRENT_TIMESTAMP, true);

MERGE INTO content (id, title, description, content_type, genre, creator, file_url, thumbnail_url,
                    required_plan, view_count, rating, created_by, created_at, available)
KEY(id)
VALUES (8, 'Epic Orchestra: Director''s Cut',
        'A sweeping orchestral film exclusive to our yearly subscribers.',
        'MOVIE', 'Classical', 'Symphony Productions',
        '/content/files/epic-orchestra.mp4', '/content/thumbs/orchestra.jpg',
        'YEARLY', 320, 4.9, 1, CURRENT_TIMESTAMP, true);

ALTER TABLE content ALTER COLUMN id RESTART WITH 20;
