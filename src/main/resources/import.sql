INSERT INTO users (id, username, password)
VALUES (1, 'guest', '$2a$10$8.UnS3K98jBWSpsU8z6W6OU96Mv5/WpSTWwD.VpS.U96Mv5/WpSTW')
ON CONFLICT (id) DO NOTHING;

INSERT IGNORE book (id_book, title, author, genre, finish_date, score, review, user_id)
VALUES
(1, 'The Count of Monte Cristo', 'Alexandre Dumas', 'Adventure', '2025-01-15', 5, 'The ultimate story of vengeance and redemption. A true masterpiece.', 1),
(2, 'The Witcher: The Last Wish', 'Andrzej Sapkowski', 'Fantasy', '2025-02-10', 4, 'Excellent introduction to Geralt of Rivia. Gritty and dark folklore.', 1),
(3, 'Pride and Prejudice', 'Jane Austen', 'Classic', '2025-03-05', 5, 'Sharp wit and beautiful character development. Unbeatable romance.', 1),
(4, 'The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', 'Contemporary', '2025-04-12', 5, 'A glamorous and heartbreaking journey through Old Hollywood.', 1),
(5, 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', '2025-05-20', 3, 'A perfect adventure. Bilbo Baggins is the most relatable hero.', 1),
(6, 'Anna Karenina', 'Leo Tolstoy', 'Classic', '2025-06-18', 3, 'A complex, tragic, and deeply psychological study of Russian society.', 1),
(7, 'Comerás flores', 'Lucía Solla', 'Fiction', '2025-07-01', 5, 'A novel that delves into the illusions of relationships, the difficulties of grief, and friendship as a refuge.', 1),
(8, 'The Name of the Wind', 'Patrick Rothfuss', 'Fantasy', '2025-08-14', 4, 'Exquisite prose and a magic system that feels grounded and real.', 1),
(9, 'The Call of Cthulhu', 'H.P. Lovecraft', 'Horror', '2025-09-22', 3, 'The cornerstone of cosmic horror. Deeply atmospheric and unsettling.', 1),
(10, 'Papyrus: The Invention of Books', 'Irene Vallejo', 'Non-Fiction', '2025-10-05', 5, 'A stunning love letter to the history of books and the written word.', 1),
(11, 'The Dawn of Everything', 'David Graeber & David Wengrow', 'Anthropology', '2025-11-12', 4, 'Challenging our fundamental beliefs about the history of humanity.', 1),
(12, 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'History', '2025-12-01', 3, 'Thought-provoking and ambitious. It changes how you see the world.', 1),
(13, 'La península de las casas vacías', 'David Uclés', 'Historical Fiction', '2025-12-15', 5, 'An epic and magical realism approach to the Spanish Civil War.', 1),
(14, 'The Shining', 'Stephen King', 'Horror', '2025-12-28', 2, 'A terrifying masterpiece of isolation and psychological breakdown.', 1),
(15, 'Murder on the Orient Express', 'Agatha Christie', 'Mystery', '2026-01-05', 4, 'The quintessential locked-room mystery with a brilliant twist.', 1),
(16, 'Frankenstein', 'Mary Shelley', 'Gothic Fiction', '2026-01-20', 5, 'The original sci-fi tragedy. Heartbreaking and deeply philosophical.', 1),
(17, '1984', 'George Orwell', 'Dystopian', '2026-01-28', 3, 'Terrifyingly relevant even today.', 1)
ON CONFLICT (id_book) DO NOTHING;