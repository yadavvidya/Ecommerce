import React from 'react';
import './dashboard-theme.css';

const sidebarItems = [
  { icon: '🛒', label: 'Products' },
  { icon: '📦', label: 'Order List' },
  { icon: '➕', label: 'Add Inventory (Admin)' },
];

interface DashboardLayoutProps {
  children: React.ReactNode;
  activeSection?: string;
  onSidebarClick?: (label: string) => void;
}

export default function DashboardLayout({ children, activeSection, onSidebarClick }: DashboardLayoutProps) {
  return (
    <div className="dashboard-root">
      <aside className="dashboard-sidebar">
        <div className="sidebar-logo">YOUR LOGO</div>
        <nav>
          <ul>
            {sidebarItems.map(item => (
              <li key={item.label} className={`sidebar-item${activeSection === item.label ? ' active' : ''}`}
                  onClick={() => onSidebarClick && onSidebarClick(item.label)}>
                <span className="sidebar-icon">{item.icon}</span>
                <span>{item.label}</span>
              </li>
            ))}
          </ul>
          <hr style={{ margin: '2rem 0', border: 0, borderTop: '1.5px solid #f2c6c6' }} />
          <div style={{ color: '#bbb', fontSize: '0.95rem', paddingLeft: 8 }}>Demo Navigation</div>
        </nav>
      </aside>
      <main className="dashboard-main">
        <header className="dashboard-topbar">
          <input className="dashboard-search" placeholder="Search" />
          <div className="dashboard-user">
            <span>English</span>
            <span className="user-avatar">J</span>
          </div>
        </header>
        <div className="dashboard-content">{children}</div>
      </main>
    </div>
  );
}
