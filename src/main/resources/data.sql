-- Demo data (PostgreSQL) for the refactored coursework project.
-- If you want an empty DB, you can delete this file.

INSERT INTO residents (resident_id, name, phone, email) VALUES
  (1, 'Мария Иванова', '+7-900-111-11-11', 'ivanova@example.com'),
  (2, 'Алексей Смирнов', '+7-900-222-22-22', 'smirnov@example.com'),
  (3, 'Екатерина Кузнецова', '+7-900-333-33-33', 'kuznetsova@example.com')
ON CONFLICT (resident_id) DO NOTHING;

INSERT INTO workers (worker_id, name, role, rating) VALUES
  (1, 'Иван Петров', 'Сантехник', 4.6),
  (2, 'Олег Сидоров', 'Электрик', 4.4),
  (3, 'Анна Диспетчер', 'Диспетчер', 4.9)
ON CONFLICT (worker_id) DO NOTHING;

INSERT INTO service_types (service_type_id, name, price, duration_minutes) VALUES
  (1, 'Сантехника', 1500.00, 60),
  (2, 'Электрика', 1800.00, 90),
  (3, 'Уборка', 900.00, 45)
ON CONFLICT (service_type_id) DO NOTHING;

INSERT INTO requests (request_id, title, description, resident_id, assigned_worker_id, service_type_id,
                      start_time, end_time, status, priority)
VALUES
  (1, 'Протекает кран', 'На кухне протекает кран, требуется ремонт.', 1, 1, 1,
   now() - interval '2 days', now() - interval '2 days' + interval '1 hour', 'IN_PROGRESS', 'HIGH'),
  (2, 'Не горит свет в подъезде', 'На 2 этаже перегорела лампа.', 2, 2, 2,
   now() - interval '1 day', now() - interval '1 day' + interval '1 hour', 'NEW', 'MEDIUM'),
  (3, 'Уборка подъезда', 'Нужна уборка после ремонта на этаже.', 3, 3, 3,
   now() - interval '6 days', now() - interval '6 days' + interval '45 min', 'DONE', 'LOW')
ON CONFLICT (request_id) DO NOTHING;

-- IMPORTANT: when you insert explicit IDs, PostgreSQL sequences don't advance by themselves.
-- Sync sequences to current max(id), so new inserts won't collide.
SELECT setval(pg_get_serial_sequence('residents', 'resident_id'), COALESCE((SELECT MAX(resident_id) FROM residents), 0));
SELECT setval(pg_get_serial_sequence('workers', 'worker_id'), COALESCE((SELECT MAX(worker_id) FROM workers), 0));
SELECT setval(pg_get_serial_sequence('service_types', 'service_type_id'), COALESCE((SELECT MAX(service_type_id) FROM service_types), 0));
SELECT setval(pg_get_serial_sequence('requests', 'request_id'), COALESCE((SELECT MAX(request_id) FROM requests), 0));
