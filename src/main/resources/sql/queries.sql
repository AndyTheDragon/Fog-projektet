SELECT mf.description
FROM material_function mf
         JOIN carport_material_function cmf ON mf.function_id = cmf.function_id
WHERE cmf.material_id = 1;

SELECT cm.material_name
FROM carport_material cm
         JOIN carport_material_function cmf ON cm.material_id = cmf.material_id
WHERE cmf.function_id = 1;
