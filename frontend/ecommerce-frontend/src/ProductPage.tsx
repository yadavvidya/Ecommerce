import React, { useState } from 'react';
import './theme.css';

interface InventoryItem {
  id: number;
  productId: number;
  availableQty: number;
  reservedQty: number;
}

interface ProductPageProps {
  product: InventoryItem;
}

export default function ProductPage({ product }: ProductPageProps) {
  // Demo static data for now, but use product prop for id, stock, etc.
  const title = `Product #${product.productId}`;
  const price = product.productId * 10 + 25;
  const description = 'A stylish product with great features.';
  const stock = product.availableQty;
  const colors = ['#232f3e', '#e57373', '#febd69'];
  const sizes = ['S', 'M', 'L'];
  const rating = 4.5;
  const reviews = 23;

  const [selectedColor, setSelectedColor] = useState(colors[0]);
  const [selectedSize, setSelectedSize] = useState(sizes[0]);
  const [quantity, setQuantity] = useState(1);
  const [message, setMessage] = useState('');

  const handleAddToCart = () => {
    setMessage('Added to cart!');
  };
  const handleBuyNow = () => {
    setMessage('Order placed!');
  };

  return (
    <div className="productpage-root">
      <div className="productpage-imgbox">
        <div className="productpage-img-placeholder">IMG</div>
      </div>
      <div className="productpage-details">
        <h2 className="productpage-title">{title}</h2>
        <div className="productpage-rating">
          {'★'.repeat(Math.floor(rating))}
          <span style={{ color: '#bbb', marginLeft: 8 }}>({reviews} reviews)</span>
        </div>
        <div className="productpage-price">${price}</div>
        <div className="productpage-desc">{description}</div>
        <div className="productpage-stock">Stock: {stock}</div>
        <div className="productpage-swatches">
          <div>Color:</div>
          {colors.map(c => (
            <span key={c} className="swatch" style={{ background: c, border: selectedColor === c ? '2px solid #232f3e' : '2px solid #fff' }}
              onClick={() => setSelectedColor(c)} />
          ))}
        </div>
        <div className="productpage-sizes">
          <div>Size:</div>
          {sizes.map(s => (
            <button key={s} className={`size-btn${selectedSize === s ? ' selected' : ''}`} onClick={() => setSelectedSize(s)}>{s}</button>
          ))}
        </div>
        <div className="productpage-actions">
          <input type="number" min={1} max={stock} value={quantity} onChange={e => setQuantity(Number(e.target.value))} className="qty-input" />
          <button className="button" onClick={handleAddToCart}>Add to Cart</button>
          <button className="button" style={{ background: 'linear-gradient(90deg,#f90 60%,#febd69 100%)', color: '#fff' }} onClick={handleBuyNow}>Buy Now</button>
        </div>
        {message && <div style={{ color: 'green', marginTop: 8 }}>{message}</div>}
      </div>
    </div>
  );
}
