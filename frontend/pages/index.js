import Head from 'next/head'
import Image from 'next/image'
import Landing from '../components/Landing'
import LandingHeader from '../components/LandingHeader'

export default function Home() {
  return (
    <div>
      <LandingHeader />
      <Landing />
    </div>
  )
}