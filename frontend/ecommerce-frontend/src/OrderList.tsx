import { useEffect, useState } from 'react';

interface Order {
  id: number;
  userId: number;
  orderStatus: string;
  totalAmount: number;
  createdAt: string;
  shippingAddress: string;
  products: number[];
}

export default function OrderList({ userId }: { userId: string }) {
  const [orders, setOrders] = useState<Order[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!userId) return;
    setLoading(true);
    fetch(`http://localhost:8081/orders/user/${userId}`)
      .then(res => res.json())
      .then(data => {
        setOrders(data);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to fetch orders');
        setLoading(false);
      });
  }, [userId]);

  if (!userId) return <div>Please enter your User ID to view orders.</div>;
  if (loading) return <div>Loading orders...</div>;
  if (error) return <div style={{ color: 'red' }}>{error}</div>;

  return (
    <div>
      <h3>Your Orders</h3>
      {orders.length === 0 ? (
        <div>No orders found.</div>
      ) : (
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {orders.map(order => (
            <li key={order.id} style={{ marginBottom: 16, background: '#f9f9f9', padding: 12, borderRadius: 6 }}>
              <b>Order #{order.id}</b> - <span className={`status ${order.orderStatus.toLowerCase()}`}>{order.orderStatus}</span><br />
              <span>Products: {order.products.join(', ')}</span><br />
              <span>Total: ${order.totalAmount}</span><br />
              <span>Placed: {new Date(order.createdAt).toLocaleString()}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
