INSERT INTO bookstore.book (TITLE, DESCRIPTION, CATEGORY, AUTHOR_ID)
VALUES

('Dom Casmurro', 'Um romance sobre a possível traição de Capitu', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Machado de Assis')),
('Memórias Póstumas de Brás Cubas', 'Uma narrativa pós-morte de Brás Cubas', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Machado de Assis')),
('Quincas Borba', 'A história de Quincas Borba e a filosofia do Humanitismo', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Machado de Assis')),

('Gabriela, Cravo e Canela', 'Romance ambientado na Bahia, em Ilhéus', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Jorge Amado')),
('Capitães de Areia', 'A vida de meninos de rua na Bahia dos anos 1930', 'DRAMA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Jorge Amado')),

('A Hora da Estrela', 'A vida de Macabea, uma nordestina inocente', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Clarice Lispector')),

('O Tempo e o Vento', 'Saga épica sobre uma família do sul do Brasil', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Érico Veríssimo')),
('Incidente em Antares', 'Narrativa satírica e fantástica em uma cidade fictícia', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Érico Veríssimo')),

('Vidas Secas', 'A luta de uma família sertaneja contra a seca', 'DRAMA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Graciliano Ramos')),
('São Bernardo', 'História de um fazendeiro e suas tragédias pessoais', 'DRAMA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Graciliano Ramos')),
('Memórias do Cárcere', 'Relato autobiográfico do autor na prisão', 'BIOGRAFIA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Graciliano Ramos')),

('Romanceiro da Inconfidência', 'Obra poética sobre a Inconfidência Mineira', 'POESIA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Cecília Meireles')),

('Ensaio sobre a Cegueira', 'Uma epidemia que causa cegueira na população', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'José Saramago')),
('O Evangelho Segundo Jesus Cristo', 'Narrativa alternativa da vida de Jesus', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'José Saramago')),

('O Auto da Compadecida', 'Uma comédia sobre o sertão nordestino', 'DRAMA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Ariano Suassuna')),

('Morte e Vida Severina', 'Poema narrativo sobre a saga do retirante Severino', 'POESIA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'João Cabral de Melo Neto')),

('O Sítio do Picapau Amarelo', 'Uma série de aventuras no sítio encantado', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Monteiro Lobato')),

('O Alquimista', 'Uma jornada espiritual em busca de um tesouro', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Paulo Coelho')),
('Brida', 'A busca de uma mulher por autoconhecimento', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Paulo Coelho')),

('Ciranda de Pedra', 'Conflitos e intrigas familiares em São Paulo', 'DRAMA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Lygia Fagundes Telles')),
('As Meninas', 'História de três jovens mulheres e seus dilemas', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Lygia Fagundes Telles')),

('Bagagem', 'Coleção de poesias de uma autora mineira', 'POESIA',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Adélia Prado')),

('O Gato de Botas', 'Recontagem do clássico conto infantil', 'FICCAO',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Ana Maria Machado')),
('Bisa Bia, Bisa Bel', 'Viagem no tempo unindo gerações de uma família', 'ROMANCE',
 (SELECT ID FROM bookstore.author WHERE NAME = 'Ana Maria Machado'));
