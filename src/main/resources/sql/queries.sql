select m.material_id, m.material_name, m.length, f.description from carport_material as m
    inner join carport_material_function on m.material_id = carport_material_function.material_id
    inner join material_function as f on carport_material_function.function_id=f.function_id;

SELECT mf.description
FROM material_function mf
         JOIN carport_material_function cmf ON mf.function_id = cmf.function_id
WHERE cmf.material_id = 1;

SELECT cm.material_name
FROM carport_material cm
         JOIN carport_material_function cmf ON cm.material_id = cmf.material_id
WHERE cmf.function_id = 1;
