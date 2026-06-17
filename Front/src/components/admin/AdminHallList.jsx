// src/components/admin/AdminHallList.jsx

import { useEffect, useState } from "react";
import { getHalls, addHall, updateHall, deleteHall } from "../../services/hallService";

export default function AdminHallList() {
    const [halls, setHalls] = useState([]);
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [editingId, setEditingId] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        fetchHalls();
    }, []);

    const fetchHalls = async () => {
        try {
            const res = await getHalls();
            setHalls(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const hallData = {
                name,
                location
            };

            if (editingId) {
                await updateHall(editingId, hallData);
            } else {
                await addHall(hallData);
            }

            setName("");
            setLocation("");
            setEditingId(null);

            fetchHalls();
        } catch (err) {
            setError(err.response?.data || "حدث خطأ");
        }
    };

    const handleEdit = (hall) => {
        setEditingId(hall.id);
        setName(hall.name);
        setLocation(hall.location);
    };

    const handleDelete = async (id) => {
        if (!window.confirm("هل تريد حذف هذه الصالة؟")) return;

        try {
            await deleteHall(id);
            fetchHalls();
        } catch (err) {
            alert(err.response?.data || "فشل الحذف");
        }
    };

    const cancelEdit = () => {
        setEditingId(null);
        setName("");
        setLocation("");
    };

    return (
        <div className="admin-movie-list">
            <div className="admin-header">
                <h2>إدارة الصالات</h2>
            </div>

            {error && <p className="error">{error}</p>}

            <form
                onSubmit={handleSubmit}
                style={{
                    display: "flex",
                    gap: "10px",
                    marginBottom: "20px",
                    flexWrap: "wrap"
                }}
            >
                <input
                    type="text"
                    placeholder="اسم الصالة"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    style={{
                        flex: 1,
                        minWidth: "200px",
                        padding: "12px"
                    }}
                />

                <input
                    type="text"
                    placeholder="الموقع"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                    required
                    style={{
                        flex: 1,
                        minWidth: "200px",
                        padding: "12px"
                    }}
                />

                <button type="submit">
                    {editingId ? "حفظ التعديل" : "+ إضافة صالة"}
                </button>

                {editingId && (
                    <button type="button" onClick={cancelEdit}>
                        إلغاء
                    </button>
                )}
            </form>

            <table>
                <thead>
                    <tr>
                        <th>اسم الصالة</th>
                        <th>الموقع</th>
                        <th>عدد الأفلام</th>
                        <th>الإجراءات</th>
                    </tr>
                </thead>

                <tbody>
                    {halls.map((hall) => (
                        <tr key={hall.id}>
                            <td>{hall.name}</td>
                            <td>{hall.location}</td>
                            <td>{hall.movieCount}</td>

                            <td>
                                <button onClick={() => handleEdit(hall)}>
                                    تعديل
                                </button>

                                <button
                                    onClick={() => handleDelete(hall.id)}
                                    className="delete-btn"
                                >
                                    حذف
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}