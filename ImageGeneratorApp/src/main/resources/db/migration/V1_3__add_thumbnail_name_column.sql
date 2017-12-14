ALTER TABLE gallery_image ADD COLUMN thumbnail_image_name TEXT;
ALTER TABLE gallery_image RENAME name TO full_image_name;