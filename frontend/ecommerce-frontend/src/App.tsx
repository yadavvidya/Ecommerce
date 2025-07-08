import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';
import './App.css';
import './theme.css';
import DashboardLayout from './DashboardLayout';
import ProductPage from './ProductPage';
import OrderList from './OrderList';

interface OrderEvent {
  orderId: number;
  eventType: string;
  status: string;
  message: string;
}

interface InventoryItem {
  id: number;
  productId: number;
  availableQty: number;
  reservedQty: number;
}

function App() {
  const [orderEvents, setOrderEvents] = useState<OrderEvent[]>([]);
  const [inventory, setInventory] = useState<InventoryItem[]>([]);
  const [userId, setUserId] = useState('');
  const [productId, setProductId] = useState('');
  const [quantity, setQuantity] = useState('');
  const [orderMessage, setOrderMessage] = useState('');
  const [invProductId, setInvProductId] = useState('');
  const [invAvailableQty, setInvAvailableQty] = useState('');
  const [invReservedQty, setInvReservedQty] = useState('');
  const [invMessage, setInvMessage] = useState('');
  const [activeSection, setActiveSection] = useState<'Products'|'Orders'|'AddInventory'>('Products');
  const [showBuyModal, setShowBuyModal] = useState(false);
  const [buyProduct, setBuyProduct] = useState<InventoryItem|null>(null);
  const [buyQuantity, setBuyQuantity] = useState('1');
  const [buyUserId, setBuyUserId] = useState('');
  const [buyMessage, setBuyMessage] = useState('');
  const [viewProduct, setViewProduct] = useState<InventoryItem|null>(null);
  const [orderUserId, setOrderUserId] = useState('');

  const sampleProduct = {
    id: 0,
    productId: 101,
    availableQty: 10,
    reservedQty: 0
  };

  // SSE for order events
  useEffect(() => {
    const eventSource = new EventSource('http://localhost:8081/order-events/stream');
    eventSource.onmessage = (event) => {
      const data: OrderEvent = JSON.parse(event.data);
      setOrderEvents((prev) => [data, ...prev]);
    };
    return () => eventSource.close();
  }, []);

  // Fetch inventory from inventory-service
  const fetchInventory = async () => {
    try {
      const res = await fetch('http://localhost:8082/inventory');
      const data = await res.json();
      setInventory(data);
    } catch (err) {
      setOrderMessage('Failed to fetch inventory.');
    }
  };

  // Place order
  const handleOrderSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setOrderMessage('');
    try {
      const res = await fetch('http://localhost:8081/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId: Number(userId),
          items: [{ productId: Number(productId), quantity: Number(quantity) }]
        })
      });
      if (res.ok) {
        setOrderMessage('Order placed successfully!');
        setUserId(''); setProductId(''); setQuantity('');
      } else {
        setOrderMessage('Failed to place order.');
      }
    } catch (err) {
      setOrderMessage('Error placing order.');
    }
  };

  // Add inventory
  const handleAddInventory = async (e: FormEvent) => {
    e.preventDefault();
    setInvMessage('');
    try {
      const res = await fetch('http://localhost:8082/inventory', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          productId: Number(invProductId),
          availableQty: Number(invAvailableQty),
          reservedQty: invReservedQty ? Number(invReservedQty) : 0
        })
      });
      if (res.ok) {
        setInvMessage('Inventory added successfully!');
        setInvProductId(''); setInvAvailableQty(''); setInvReservedQty('');
        fetchInventory();
      } else {
        setInvMessage('Failed to add inventory.');
      }
    } catch (err) {
      setInvMessage('Error adding inventory.');
    }
  };

  // Sidebar navigation handler
  const handleSidebarClick = (label: string) => {
    if (label === 'Products') setActiveSection('Products');
    else if (label === 'Order List') setActiveSection('Orders');
    else if (label === 'Add Inventory (Admin)') setActiveSection('AddInventory');
  };

  // Buy Now modal order handler
  const handleBuyNow = async (e: FormEvent) => {
    e.preventDefault();
    setBuyMessage('');
    if (!buyProduct) return;
    try {
      const res = await fetch('http://localhost:8081/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userId: Number(buyUserId),
          items: [{ productId: buyProduct.productId, quantity: Number(buyQuantity) }]
        })
      });
      if (res.ok) {
        setBuyMessage('Order placed successfully!');
        setBuyQuantity('1');
        setBuyUserId('');
      } else {
        setBuyMessage('Failed to place order.');
      }
    } catch {
      setBuyMessage('Error placing order.');
    }
  };

  return (
    <DashboardLayout activeSection={activeSection} onSidebarClick={handleSidebarClick}>
      {viewProduct ? (
        <div>
          <button className="button" style={{ marginBottom: 24 }} onClick={() => setViewProduct(null)}>&larr; Back to Products</button>
          <ProductPage product={viewProduct} />
        </div>
      ) : activeSection === 'Products' && (
        <div style={{ marginBottom: 32 }}>
          <h2 style={{ fontWeight: 700, fontSize: '1.7rem', marginBottom: 24 }}>Products</h2>
          <div className="product-grid">
            {inventory.length === 0 ? (
              <div className="product-card" style={{ cursor: 'pointer' }} onClick={() => setViewProduct(sampleProduct)}>
                <div className="product-img-placeholder">IMG</div>
                <div className="product-title">Sample Product</div>
                <div className="product-stock">Stock: 10</div>
                <div className="product-price">$125</div>
                <button className="button" style={{ width: '100%', marginTop: 8 }}
                  onClick={e => { e.stopPropagation(); setBuyProduct(sampleProduct); setShowBuyModal(true); setBuyQuantity('1'); setBuyUserId(''); setBuyMessage(''); }}>
                  Buy Now
                </button>
              </div>
            ) : (
              inventory.map(item => (
                <div className="product-card" key={item.id} onClick={() => setViewProduct(item)} style={{ cursor: 'pointer' }}>
                  <div className="product-img-placeholder">IMG</div>
                  <div className="product-title">Product #{item.productId}</div>
                  <div className="product-stock">Stock: {item.availableQty}</div>
                  <div className="product-price">${(item.productId * 10 + 25)}</div>
                  <button className="button" style={{ width: '100%', marginTop: 8 }}
                    onClick={e => { e.stopPropagation(); setBuyProduct(item); setShowBuyModal(true); setBuyQuantity('1'); setBuyUserId(''); setBuyMessage(''); }}>
                    Buy Now
                  </button>
                </div>
              ))
            )}
          </div>
        </div>
      )}
      {activeSection === 'Orders' && (
        <section className="card" style={{ marginBottom: 32 }}>
          <div className="section-title">Order List</div>
          <form onSubmit={e => { e.preventDefault(); setOrderUserId(userId); }} style={{ marginBottom: 16 }}>
            <input type="number" placeholder="Enter User ID" value={userId} onChange={e => setUserId(e.target.value)} required />
            <button type="submit" className="button">View My Orders</button>
          </form>
          <OrderList userId={orderUserId} />
        </section>
      )}
      {activeSection === 'AddInventory' && (
        <section className="card">
          <div className="section-title">Add Inventory (Admin)</div>
          <form onSubmit={handleAddInventory} style={{ display: 'flex', gap: 16, flexWrap: 'wrap', alignItems: 'center', marginBottom: 16 }}>
            <input type="number" placeholder="Product ID" value={invProductId} onChange={e => setInvProductId(e.target.value)} required />
            <input type="number" placeholder="Available Qty" value={invAvailableQty} onChange={e => setInvAvailableQty(e.target.value)} required />
            <input type="number" placeholder="Reserved Qty (optional)" value={invReservedQty} onChange={e => setInvReservedQty(e.target.value)} />
            <button type="submit" className="button">Add Inventory</button>
          </form>
          {invMessage && <div style={{ color: invMessage.includes('success') ? 'green' : 'red', marginBottom: 8 }}>{invMessage}</div>}
          <button onClick={fetchInventory} className="button" style={{ marginBottom: 16 }}>Refresh Inventory</button>
        </section>
      )}
      {/* Buy Now Modal */}
      {showBuyModal && buyProduct && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h3>Buy Product #{buyProduct.productId}</h3>
            <form onSubmit={handleBuyNow} style={{ display: 'flex', flexDirection: 'column', gap: 12 }}>
              <input type="number" placeholder="User ID" value={buyUserId} onChange={e => setBuyUserId(e.target.value)} required />
              <input type="number" min={1} max={buyProduct.availableQty} placeholder="Quantity" value={buyQuantity} onChange={e => setBuyQuantity(e.target.value)} required />
              <button type="submit" className="button">Confirm Purchase</button>
              <button type="button" className="button" style={{ background: '#eee', color: '#e57373' }} onClick={() => setShowBuyModal(false)}>Cancel</button>
              {buyMessage && <div style={{ color: buyMessage.includes('success') ? 'green' : 'red' }}>{buyMessage}</div>}
            </form>
          </div>
        </div>
      )}
    </DashboardLayout>
  );
}

export default App;
